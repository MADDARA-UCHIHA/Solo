create extension if not exists "pgcrypto";

create table if not exists public.users (
    id uuid primary key references auth.users(id) on delete cascade,
    public_user_id text not null unique,
    display_name text,
    created_at timestamptz not null default now(),
    updated_at timestamptz not null default now()
);

create table if not exists public.virtual_number_offers (
    id uuid primary key default gen_random_uuid(),
    e164_number text not null unique,
    formatted_number text not null,
    country_code text not null,
    tier text not null check (tier in ('standard', 'vip', 'custom')),
    price_minor bigint not null check (price_minor >= 0),
    currency char(3) not null default 'USD',
    status text not null default 'available'
        check (status in ('available', 'reserved', 'assigned', 'disabled')),
    created_at timestamptz not null default now()
);

create table if not exists public.virtual_number_assignments (
    id uuid primary key default gen_random_uuid(),
    user_id uuid not null references auth.users(id) on delete cascade,
    offer_id uuid not null references public.virtual_number_offers(id),
    status text not null default 'pending'
        check (status in ('pending', 'active', 'released')),
    assigned_at timestamptz,
    released_at timestamptz,
    created_at timestamptz not null default now()
);

create unique index if not exists one_active_number_per_offer
    on public.virtual_number_assignments (offer_id)
    where status in ('pending', 'active');

create table if not exists public.number_orders (
    id uuid primary key default gen_random_uuid(),
    user_id uuid not null default auth.uid() references auth.users(id) on delete cascade,
    offer_id uuid references public.virtual_number_offers(id),
    requested_tier text not null check (requested_tier in ('standard', 'vip', 'custom')),
    status text not null default 'pending'
        check (status in ('pending', 'payment_required', 'paid', 'fulfilled', 'cancelled')),
    created_at timestamptz not null default now()
);

alter table public.users enable row level security;
alter table public.virtual_number_offers enable row level security;
alter table public.virtual_number_assignments enable row level security;
alter table public.number_orders enable row level security;

create policy "Users can read their account"
    on public.users for select
    to authenticated using (id = auth.uid());

create policy "Anyone can browse available offers"
    on public.virtual_number_offers for select
    to anon, authenticated using (status = 'available');

create policy "Users can read their assignments"
    on public.virtual_number_assignments for select
    to authenticated using (user_id = auth.uid());

create policy "Users can create their own orders"
    on public.number_orders for insert
    to authenticated with check (user_id = auth.uid());

create policy "Users can read their own orders"
    on public.number_orders for select
    to authenticated using (user_id = auth.uid());
