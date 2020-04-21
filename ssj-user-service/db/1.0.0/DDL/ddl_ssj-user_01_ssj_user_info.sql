DROP TABLE IF EXISTS "public"."ssj_user_info";
DROP SEQUENCE IF EXISTS "public"."ssj_user_info_id_seq";
CREATE SEQUENCE "public"."ssj_user_info_id_seq"
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE "public"."ssj_user_info" (
	"id" int8 NOT NULL DEFAULT nextval('ssj_user_info_id_seq'::regclass),
	"user_id" varchar(100) NOT NULL,
	"real_name" varchar(100),
	"card_id" varchar(100),
	"phone_number" varchar(100),
	"union_id" varchar(100),
	"nick_name" varchar(100) NOT NULL,
	"password" varchar(100) NOT NULL,
	"is_valid" int2 NOT NULL DEFAULT 1,
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
create trigger ssj_user_info_trigger before update on ssj_user_info for each row execute procedure update_timestamp();

COMMENT ON TABLE "public"."ssj_user_info" IS '用户信息表';
COMMENT ON COLUMN "public"."ssj_user_info"."id" IS '主键,自增长';
COMMENT ON COLUMN "public"."ssj_user_info"."user_id" IS '用户id(唯一)';
COMMENT ON COLUMN "public"."ssj_user_info"."real_name" IS '真实姓名';
COMMENT ON COLUMN "public"."ssj_user_info"."card_id" IS '身份证号(唯一)';
COMMENT ON COLUMN "public"."ssj_user_info"."phone_number" IS '手机号(唯一)';
COMMENT ON COLUMN "public"."ssj_user_info"."union_id" IS 'unionId(唯一)';
COMMENT ON COLUMN "public"."ssj_user_info"."nick_name" IS '昵称';
COMMENT ON COLUMN "public"."ssj_user_info"."password" IS '密码';
COMMENT ON COLUMN "public"."ssj_user_info"."is_valid" IS '是否生效(0:否,1:是)';
COMMENT ON COLUMN "public"."ssj_user_info"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."ssj_user_info"."update_time" IS '更新时间';

ALTER SEQUENCE "public"."ssj_user_info_id_seq" OWNED BY "public"."ssj_user_info"."id";

ALTER TABLE "public"."ssj_user_info" ADD CONSTRAINT "ssj_user_info_pk_id" PRIMARY KEY ("id");

CREATE UNIQUE INDEX ssj_user_info_ud_user_id ON "public"."ssj_user_info" (user_id);

CREATE UNIQUE INDEX ssj_user_info_ud_card_id ON "public"."ssj_user_info" (card_id);

CREATE UNIQUE INDEX ssj_user_info_ud_phone_number ON "public"."ssj_user_info" (phone_number);

CREATE UNIQUE INDEX ssj_user_info_ud_union_id ON "public"."ssj_user_info" (union_id);
