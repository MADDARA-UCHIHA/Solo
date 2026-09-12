create extension if not exists pgcrypto;

create table if not exists public.user_trial_accounts (
    id uuid primary key default gen_random_uuid(),
    email text,
    device_id text not null unique,
    created_at timestamptz not null default now(),
    subscription_end_date timestamptz not null default (now() + interval '7 days')
);

create or replace function public.set_trial_expiry()
returns trigger
language plpgsql
as $$
begin
    if NEW.subscription_end_date is null then
        NEW.subscription_end_date := NEW.created_at + interval '7 days';
    end if;
    return NEW;
end;
$$;

drop trigger if exists trg_set_trial_expiry on public.user_trial_accounts;

create trigger trg_set_trial_expiry
before insert or update of created_at, subscription_end_date
on public.user_trial_accounts
for each row
execute function public.set_trial_expiry();
