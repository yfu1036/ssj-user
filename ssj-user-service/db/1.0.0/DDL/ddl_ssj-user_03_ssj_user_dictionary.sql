DROP TABLE IF EXISTS "public"."ssj_user_dictionary";
DROP SEQUENCE IF EXISTS "public"."ssj_user_dictionary_id_seq";
CREATE SEQUENCE "public"."ssj_user_dictionary_id_seq"
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE "public"."ssj_user_dictionary" (
	"id" int8 NOT NULL DEFAULT nextval('ssj_user_dictionary_id_seq'::regclass),
	"type" varchar(100) NOT NULL,
	"type_desc" varchar(100) NOT NULL,
	"code" varchar(50) NOT NULL,
	"name" varchar(100) NOT NULL,
	"parent_code" varchar(50),
	"create_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP,
	"update_time" timestamp(6) NOT NULL DEFAULT CURRENT_TIMESTAMP
);

create or replace function update_timestamp() returns trigger as
$$
begin 
	new.update_time = CURRENT_TIMESTAMP;
	return new;
end
$$ language plpgsql;
create trigger ssj_user_dictionary_trigger before update on ssj_user_dictionary for each row execute procedure update_timestamp();

COMMENT ON TABLE "public"."ssj_user_dictionary" IS '用户数据字典表';
COMMENT ON COLUMN "public"."ssj_user_dictionary"."id" IS '主键,自增长';
COMMENT ON COLUMN "public"."ssj_user_dictionary"."type" IS '类型';
COMMENT ON COLUMN "public"."ssj_user_dictionary"."type_desc" IS '类型描述';
COMMENT ON COLUMN "public"."ssj_user_dictionary"."code" IS '编码';
COMMENT ON COLUMN "public"."ssj_user_dictionary"."name" IS '编码值';
COMMENT ON COLUMN "public"."ssj_user_dictionary"."parent_code" IS '上级编码';
COMMENT ON COLUMN "public"."ssj_user_dictionary"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."ssj_user_dictionary"."update_time" IS '更新时间';

ALTER SEQUENCE "public"."ssj_user_dictionary_id_seq" OWNED BY "public"."ssj_user_dictionary"."id";

ALTER TABLE "public"."ssj_user_dictionary" ADD CONSTRAINT "ssj_user_dictionary_pk_id" PRIMARY KEY ("id");

CREATE INDEX ssj_user_dictionary_idx_type ON "public"."ssj_user_dictionary" (type);
