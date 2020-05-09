DROP TABLE IF EXISTS "public"."ssj_user_account_info";
DROP SEQUENCE IF EXISTS "public"."ssj_user_account_info_id_seq";
CREATE SEQUENCE "public"."ssj_user_account_info_id_seq"
INCREMENT 1
MINVALUE 1
MAXVALUE 9223372036854775807
START 1
CACHE 1;

CREATE TABLE "public"."ssj_user_account_info" (
	"id" int8 NOT NULL DEFAULT nextval('ssj_user_account_info_id_seq'::regclass),
	"user_id" varchar(100) NOT NULL,
	"account_type" varchar(20) NOT NULL,
	"account_source" varchar(50) NOT NULL,
	"account_register" varchar(50),
	"account_id" varchar(100) NOT NULL,
	"login_password" varchar(100) NOT NULL,
	"pay_password" varchar(100),
	"secret" varchar(200),
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
create trigger ssj_user_account_info_trigger before update on ssj_user_account_info for each row execute procedure update_timestamp();

COMMENT ON TABLE "public"."ssj_user_account_info" IS '用户账户信息表';
COMMENT ON COLUMN "public"."ssj_user_account_info"."id" IS '主键,自增长';
COMMENT ON COLUMN "public"."ssj_user_account_info"."user_id" IS '用户id';
COMMENT ON COLUMN "public"."ssj_user_account_info"."account_type" IS '账户类型';
COMMENT ON COLUMN "public"."ssj_user_account_info"."account_source" IS '账户来源';
COMMENT ON COLUMN "public"."ssj_user_account_info"."account_register" IS '账户注册地';
COMMENT ON COLUMN "public"."ssj_user_account_info"."account_id" IS '账户id';
COMMENT ON COLUMN "public"."ssj_user_account_info"."login_password" IS '账户登录密码';
COMMENT ON COLUMN "public"."ssj_user_account_info"."pay_password" IS '账户交易密码';
COMMENT ON COLUMN "public"."ssj_user_account_info"."secret" IS '账户秘密信息';
COMMENT ON COLUMN "public"."ssj_user_account_info"."is_valid" IS '是否生效(0:否,1:是)';
COMMENT ON COLUMN "public"."ssj_user_account_info"."create_time" IS '创建时间';
COMMENT ON COLUMN "public"."ssj_user_account_info"."update_time" IS '更新时间';

ALTER SEQUENCE "public"."ssj_user_account_info_id_seq" OWNED BY "public"."ssj_user_account_info"."id";

ALTER TABLE "public"."ssj_user_account_info" ADD CONSTRAINT "ssj_user_account_info_pk_id" PRIMARY KEY ("id");

CREATE INDEX ssj_user_account_info_idx_user_id ON "public"."ssj_user_account_info" (user_id,account_type,account_source);

CREATE INDEX ssj_user_account_info_ud_account_id ON "public"."ssj_user_account_info" (account_id);
