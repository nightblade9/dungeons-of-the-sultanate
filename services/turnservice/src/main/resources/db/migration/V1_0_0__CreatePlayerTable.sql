-- Table: public.player_turns
CREATE TABLE IF NOT EXISTS public.player_turns
(
    user_id character varying(255) NOT NULL,
    last_turn_tick_utc timestamp without time zone, -- UTC
    num_turns integer NOT NULL,
    CONSTRAINT player_turns_pkey PRIMARY KEY (user_id)
)
