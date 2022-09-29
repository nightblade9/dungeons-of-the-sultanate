-- Table: public.player_turns

-- DROP TABLE IF EXISTS public.player_turns;

CREATE TABLE IF NOT EXISTS public.player_turns
(
    user_id character varying(255) COLLATE pg_catalog."default" NOT NULL,
    last_turn_tick_utc timestamp without time zone, -- UTC
    num_turns integer NOT NULL,
    CONSTRAINT player_turns_pkey PRIMARY KEY (user_id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS public.player_turns
    OWNER to postgres;