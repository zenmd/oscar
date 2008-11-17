--------------------------------------------------------
-- Export file for user QUATROSHELTER                 --
-- Created by Administrator on 11/17/2008, 1:08:48 PM --
--------------------------------------------------------

spool QutroShelterDB.log

prompt
prompt Creating sequence HIBERNATE_SEQUENCE
prompt ====================================
prompt
create sequence HIBERNATE_SEQUENCE
minvalue 1
maxvalue 999999999999999999999999999
start with 258401
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ADMISSION_ID
prompt ==================================
prompt
create sequence SEQ_ADMISSION_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 433220
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ATTACHMENT_ID
prompt ===================================
prompt
create sequence SEQ_ATTACHMENT_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 433020
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CASEMGMT_ISSUE
prompt ====================================
prompt
create sequence SEQ_CASEMGMT_ISSUE
minvalue 1
maxvalue 999999999999999999999999999
start with 850
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CLIENT_HISTORY_ID
prompt =======================================
prompt
create sequence SEQ_CLIENT_HISTORY_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 433760
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CLIENT_REFERRAL_ID
prompt ========================================
prompt
create sequence SEQ_CLIENT_REFERRAL_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 433340
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_DEMOGRAPHIC_MERGED
prompt ========================================
prompt
create sequence SEQ_DEMOGRAPHIC_MERGED
minvalue 1
maxvalue 999999999999999999999999999
start with 181
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_DEMOGRAPHIC_RECORDID
prompt ==========================================
prompt
create sequence SEQ_DEMOGRAPHIC_RECORDID
minvalue 1
maxvalue 999999999999999999999999999
start with 433260
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_INCIDENT_ID
prompt =================================
prompt
create sequence SEQ_INCIDENT_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 433020
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_INTAKE_ANSWER_ID
prompt ======================================
prompt
create sequence SEQ_INTAKE_ANSWER_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 450980
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_INTAKE_ID
prompt ===============================
prompt
create sequence SEQ_INTAKE_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 433340
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LOGTABLE
prompt ==============================
prompt
create sequence SEQ_LOGTABLE
minvalue 1
maxvalue 999999999999999999999999999
start with 40981
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LOG_ID
prompt ============================
prompt
create sequence SEQ_LOG_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_PROGRAM_OCCUPANCY
prompt =======================================
prompt
create sequence SEQ_PROGRAM_OCCUPANCY
minvalue 1
maxvalue 999999999999999999999999999
start with 216161
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_SDMT_IN
prompt =============================
prompt
create sequence SEQ_SDMT_IN
minvalue 1
maxvalue 999999999999999999999999999
start with 101
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_SDMT_OUT
prompt ==============================
prompt
create sequence SEQ_SDMT_OUT
minvalue 1
maxvalue 999999999999999999999999999
start with 773021
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TICKLER_ID
prompt ================================
prompt
create sequence SEQ_TICKLER_ID
minvalue 1
maxvalue 999999999999999999999999999
start with 1020
increment by 1
cache 20;

prompt
prompt Creating view V_FACILITY_BED_COUNT
prompt ==================================
prompt
create or replace view v_facility_bed_count as
select b.id facility_id, count(*) as beds
from
bed a,
facility b,
room c
where
 a.room_id=c.room_id
and
 c.facility_id = b.id
and c.active=1
and b.active=1
and a.active=1
group by b.id
/

prompt
prompt Creating view V_GRAND_TOTAL_BEDS
prompt ================================
prompt
create or replace view v_grand_total_beds as
select count (*) as grand_total_bed_count
from bed a
where a.active=1
/

prompt
prompt Creating view V_LK_INTAKE
prompt =========================
prompt
create or replace view v_lk_intake as
select 'GEN' prefix, code, description, isactive,decode(displayorder,null,0,displayorder) displayorder from LST_GENDER
union
select 'RFB' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from LST_REFERREDBY
union
select 'RFT' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from LST_REFERREDTO
union
select 'SCA' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from lst_statusincanada
union
select 'RNA' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from lst_reasonnoadmit
union
select 'CSA' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from lst_cursleeparrangement
union
select 'SIM' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from lst_sourceincome
union
select 'LBF' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from lst_livedbefore
union
select 'AOI' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from lst_aboriginal
union
select 'LHL' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from lst_lengthofhomeless
union
select 'RHL' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from lst_reasonforhomeless
union
select 'RSV' prefix, to_char(code) code, description, isactive,decode(displayorder,null,0,displayorder)  displayorder from lst_reasonforservice
/

prompt
prompt Creating view V_LK_NAME
prompt =======================
prompt
CREATE OR REPLACE VIEW V_LK_NAME AS
SELECT 0 grandParentID, 0 parentID, BED_TYPE_ID ID, Name Description
FROM LST_BED_TYPE a
/

prompt
prompt Creating view V_LK_ORG
prompt ======================
prompt
CREATE OR REPLACE VIEW V_LK_ORG AS
SELECT 0 grandParentID, 0 parentID, ID, Name Description
FROM AGENCY a
/

prompt
prompt Creating view V_LOOKUPTABLE
prompt ===========================
prompt
create or replace view v_lookuptable as
select tableid as code, description, activeyn, 0 as displayorder, moduleid,TABLE_NAME,TREECODE_LENGTH, isTree, tableid from app_lookuptable
where activeyn=1
/

prompt
prompt Creating view V_LST_REPORTGROUP
prompt ===============================
prompt
create or replace view v_lst_reportgroup as
select id CODE, description,  note, orderbyindex displayorder, decode(activeyn,'Y',1,0) activeyn
from report_lk_reportgroup
/

prompt
prompt Creating view V_LST_REPORT_QGVIEW
prompt =================================
prompt
create or replace view v_lst_report_qgview as
select qgviewcode CODE, description, groupcode parentcode, note, activeyn
from report_qgviewsummary
/

prompt
prompt Creating view V_PRN_CONSENT
prompt ===========================
prompt
CREATE OR REPLACE VIEW V_PRN_CONSENT AS
SELECT
  a.id,
  a.demographic_no client_id,
  a.provider_no,
  a.NOTES,
  a.AGENCYNAME,
  a.CONTACTNAME,
  a.CONTACTTITLE,
  a.CONTACTPHONE,
  a.STATEPURPOSE,
  a.STARTDATE,
  a.ENDDATE,
  a.DATE_SIGNED,
  a.STATUS,
  a.PROGRAM_ID,
  C.LAST_NAME STAFF_LAST_NAME,
  C.FIRST_NAME STAFF_FIRST_NAME,
  C.PROVIDER_TYPE STAFF_TYPE,
  C.PHONE STAFF_PHONE,
  b.name program_name,
  b.descr program_description,
  b.address prog_address,
  b.phone prog_phone,
  b.emergency_number prog_emrg_num,
  b.location prog_location,
  b.max_allowed prog_max_occ,
  b.num_of_members prog_numb_members,
  b.program_status,
  B.FACILITY_ID,
  B.SHELTER_ID,
  DDDD.NAME SHELTER_NAME,
  DDDD.CONTACT_NAME SHELTER_CONTACT,
  DDDD.STREET_1,
  DDDD.STREET_2,
  DDDD.PROVINCE,
  DDDD.POSTAL_CODE,
  DDDD.TELEPHONE,
  DDDD.FAX,
  D.NAME FACILITY_NAME,
  D.DESCRIPTION FACILITY_DESC,
  D.ORG_ID,
  DD.DESCRIPTION ORGANIZATION_NAME,
  D.SECTOR_ID,
  DDD.DESCRIPTION SECTOR,
  f.last_name last_name,
  f.first_name first_name,
  g.signature
FROM
  consent_detail a,
  PROGRAM b,
  PROVIDER C,
  FACILITY D,
  LST_ORGANIZATION DD,
  LST_SECTOR DDD,
  LST_SHELTER DDDD,
  DEMOGRAPHIC f,
  (select * from signature sg where sg.module_name='consent') g
WHERE
     a.program_id=b.program_id
     and
     a.provider_no=C.PROVIDER_NO
     and
     B.FACILITY_ID=D.ID
     AND
     DDDD.ORGID=DD.ID
     AND
     D.SECTOR_ID=DDD.ID
     AND
     B.SHELTER_ID=DDDD.ID
     AND
     a.demographic_no=f.demographic_no
     and a.id =g.record_id(+)
/

prompt
prompt Creating view V_PROGRAM
prompt =======================
prompt
CREATE OR REPLACE VIEW V_PROGRAM AS
SELECT
A.*,
C.DESCRIPTION SECTOR,
D.CODECSV ORGCD
FROM
PROGRAM A,
FACILITY B,
LST_SECTOR C,
LST_ORGCD d
WHERE
A.FACILITY_ID = B.ID
AND
B.SECTOR_ID = C.ID
AND
'P' || A.PROGRAM_ID = d.code
/

prompt
prompt Creating view V_PROGRAM_BED_COUNT
prompt =================================
prompt
create or replace view v_program_bed_count as
select program_id, count(*) as beds
from (
select c.*, b.program_id
from program a, room b, bed c, facility d
where a.facility_id=d.id and a.program_id = b.program_id and b.room_id = c.room_id
and d.active=1 and b.active=1 and c.active=1
)
group by program_id
/

prompt
prompt Creating view V_PROG_SECTOR
prompt ===========================
prompt
create or replace view v_prog_sector as
select
a.program_id,
a.shelter_id,
c.id facility_id,
b.description sector
from
program a,
lst_sector b,
facility c
where
a.facility_id=c.id
and
c.sector_id=b.id
/

prompt
prompt Creating function F_ORG_FACILITY
prompt ================================
prompt
CREATE OR REPLACE FUNCTION "F_ORG_FACILITY" /* Display organization level descriptions from level 3
*/

(
   p_orgcd8 varchar2
) RETURN VARCHAR2

IS
	v_orgcd varchar(72);
	v_desc varchar2(1080);
	v_code varchar2(72);
	v_tmp  varchar2(120);
	v_Char1 varchar2(1);
	v_Char2 varchar2(1);
  v_idx1 int;
  v_idx2 int;
	cursor c_ORGCD IS
		select description from lst_orgcd where code=v_code;
BEGIN

  BEGIN
       SELECT FULLCODE INTO v_orgcd
       FROM LST_ORGCD
       WHERE CODE = p_orgcd8;
  EXCEPTION
           WHEN NO_DATA_FOUND THEN RETURN '';
  END;

	v_Char1 := 'F';
	v_Char2 := 'P';
	v_desc := '';

  v_idx1 := INSTR(v_orgCd,v_CHAR1,1);
  v_idx2 := INSTR(v_orgCd,v_CHAR2,1);

  if(v_idx1) > 0 THEN
     if (v_idx2 > 0) THEN
     		v_code := substr(v_orgcd,v_idx1, v_idx2-v_idx1);
     else
     		v_code := substr(v_orgcd,v_idx1);
     end if;


   		Open c_ORGCD;
	  	Fetch c_ORGCD Into v_tmp;
		  If c_ORGCD%NOTFOUND Then
		    null;
		  End if;
		  close c_ORGCD;

		  v_desc := v_desc || v_tmp;
   END iF;
	Return v_desc;
END;
/

prompt
prompt Creating function F_ORG_ORG
prompt ===========================
prompt
CREATE OR REPLACE FUNCTION "F_ORG_ORG" /* Display organization level descriptions from level 3
*/

(
   p_orgcd8 varchar2
) RETURN VARCHAR2

IS
	v_orgcd varchar(72);
	v_desc varchar2(1080);
	v_code varchar2(72);
	v_tmp  varchar2(120);
	v_Char1 varchar2(1);
	v_Char2 varchar2(1);
  v_idx1 int;
  v_idx2 int;
	cursor c_ORGCD IS
		select description from lst_orgcd where code=v_code;
BEGIN

  BEGIN
       SELECT FULLCODE INTO v_orgcd
       FROM LST_ORGCD
       WHERE CODE = p_orgcd8;
  EXCEPTION
           WHEN NO_DATA_FOUND THEN RETURN '';
  END;

	v_Char1 := 'O';
	v_Char2 := 'S';
	v_desc := '';

  v_idx1 := INSTR(v_orgCd,v_CHAR1,1);
  v_idx2 := INSTR(v_orgCd,v_CHAR2,1);

  if(v_idx1) > 0 THEN
     if (v_idx2 > 0) THEN
     		v_code := substr(v_orgcd,v_idx1, v_idx2-v_idx1);
     else
     		v_code := substr(v_orgcd,v_idx1);
     end if;


   		Open c_ORGCD;
	  	Fetch c_ORGCD Into v_tmp;
		  If c_ORGCD%NOTFOUND Then
		    null;
		  End if;
		  close c_ORGCD;

		  v_desc := v_desc || v_tmp;
   END iF;
	Return v_desc;
END;
/

prompt
prompt Creating function F_ORG_PROGRAM
prompt ===============================
prompt
CREATE OR REPLACE FUNCTION "F_ORG_PROGRAM" /* Display organization level descriptions from level 3
*/

(
   p_orgcd8 varchar2
) RETURN VARCHAR2

IS
	v_orgcd varchar(72);
	v_desc varchar2(1080);
	v_code varchar2(72);
	v_tmp  varchar2(120);
	v_Char1 varchar2(1);
	v_Char2 varchar2(1);
  v_idx1 int;
  v_idx2 int;
	cursor c_ORGCD IS
		select description from lst_orgcd where code=v_code;
BEGIN

  BEGIN
       SELECT FULLCODE INTO v_orgcd
       FROM LST_ORGCD
       WHERE CODE = p_orgcd8;
  EXCEPTION
           WHEN NO_DATA_FOUND THEN RETURN '';
  END;

	v_Char1 := 'P';
	v_Char2 := 'Z';
	v_desc := '';

  v_idx1 := INSTR(v_orgCd,v_CHAR1,1);
  v_idx2 := INSTR(v_orgCd,v_CHAR2,1);

  if(v_idx1) > 0 THEN
     if (v_idx2 > 0) THEN
     		v_code := substr(v_orgcd,v_idx1, v_idx2-v_idx1);
     else
     		v_code := substr(v_orgcd,v_idx1);
     end if;


   		Open c_ORGCD;
	  	Fetch c_ORGCD Into v_tmp;
		  If c_ORGCD%NOTFOUND Then
		    null;
		  End if;
		  close c_ORGCD;

		  v_desc := v_desc || v_tmp;
   END iF;
	Return v_desc;
END;
/

prompt
prompt Creating view V_REP_ADMISSION
prompt =============================
prompt
CREATE OR REPLACE VIEW V_REP_ADMISSION AS
SELECT
A.INTAKE_ID,
A.AM_ID ADMISSION_ID,
A.CLIENT_ID,
D.LAST_NAME CLIENT_LASTNAME,
D.FIRST_NAME CLIENT_FIRSTNAME,
A.ADMISSION_DATE,
A.PROVIDER_NO STAFF_ID,
E.LAST_NAME||',' ||E.FIRST_NAME STAFF_NAME,
A.ADMISSION_STATUS,
A.ADMISSION_FROM_TRANSFER,
A.ADMISSION_NOTES,
C.ORGCD,
f_org_program('P'||A.PROGRAM_ID) PROGRAM,
f_org_facility('P'||A.PROGRAM_ID) FACILITY,
f_org_org('P'||A.PROGRAM_ID) ORGANIZATION,
C.SECTOR,
A.DISCHARGE_DATE,
A.DISCHARGE_FROM_TRANSFER,
A.DISCHARGE_NOTES,
A.DISCHARGEREASON,
A.CLIENTSTATUS_ID,
A.AUTOMATIC_DISCHARGE
FROM
ADMISSION A,
LST_ORGCD B,
V_PROGRAM C,
DEMOGRAPHIC D,
PROVIDER E
WHERE
A.CLIENT_ID=D.DEMOGRAPHIC_NO
AND
A.PROVIDER_NO=E.PROVIDER_NO
AND
A.PROGRAM_ID=C.PROGRAM_ID
and
'P'||a.program_id=b.code(+)
/

prompt
prompt Creating view V_REP_ADMISSION_SUMMARY
prompt =====================================
prompt
CREATE OR REPLACE VIEW V_REP_ADMISSION_SUMMARY AS
SELECT
A.INTAKE_ID,
A.AM_ID ADMISSION_ID,
A.CLIENT_ID,
D.LAST_NAME CLIENT_LAST_NAME,
D.FIRST_NAME CLIENT_FIRST_NAME,
A.ADMISSION_DATE,
A.PROVIDER_NO STAFF_ID,
E.LAST_NAME||',' ||E.FIRST_NAME STAFF_NAME,
A.ADMISSION_STATUS STATUS,
C.ORGCD,
f_org_program('P'||A.PROGRAM_ID) PROGRAM,
f_org_facility('P'||A.PROGRAM_ID) FACILITY,
f_org_org('P'||A.PROGRAM_ID) ORGANIZATION,
C.SECTOR,
C.TYPE PROGRAM_TYPE,
A.DISCHARGE_DATE,
A.DISCHARGE_FROM_TRANSFER,
A.DISCHARGE_NOTES,
F.DESCRIPTION DISCHARGE_REASON,
F.CODE DISCHARGE_CODE
FROM
ADMISSION A,
LST_ORGCD B,
V_PROGRAM C,
DEMOGRAPHIC D,
PROVIDER E,
LST_DISCHARGE_REASON F
WHERE
A.CLIENT_ID=D.DEMOGRAPHIC_NO
AND
A.PROVIDER_NO=E.PROVIDER_NO
AND
A.PROGRAM_ID=C.PROGRAM_ID
AND
A.DISCHARGEREASON=F.CODE
and
'P'||a.program_id=b.code(+)
/

prompt
prompt Creating view V_REP_BEDLOG
prompt ==========================
prompt
CREATE OR REPLACE VIEW V_REP_BEDLOG AS
SELECT
a.client_id client_id,
  a.admission_date admission_date,
  a.discharge_date discharge_date,
  b.name program_name,
  b.descr program_description,
  c.bed_id bed_id,
  c.name bed_name,
  d.room_id room_id,
  d.name room_name,
  f.last_name last_name,
  f.first_name first_name,
  b.orgCd
FROM
  admission a,
  v_program b,
  bed c,
  room d,
  demographic f
WHERE
     a.program_id=b.program_id
     and
     a.program_id=d.program_id
     and
     d.room_id=c.room_id
     and
     a.client_id=f.demographic_no
/

prompt
prompt Creating view V_REP_BED_CHECK
prompt =============================
prompt
create or replace view v_rep_bed_check as
select

a.admission_date Admission_Date,
a.ovpass_startdate Overnight_Pass_Start,
a.ovpass_enddate Overnight_Pass_End,
a.am_id AdmissionID,
b.first_name First_Name,
b.last_name Last_Name,
a.client_id Client_No,
(select r.name from room r where r.room_id=c.room_id) as Room,
(select b.name from bed b where b.bed_id=c.bed_id) as Bed,
a.intake_id IntakeID,
(select if.intake_head_id from intake_family if where if.intake_id = a.intake_id) as family_head_intakeID,
d.name ProgramName,
d.descr ProgramDescription,
d.program_id recordid

from
admission a,
demographic b,
room_demographic c,
program d

where
a.admission_status='admitted'
and a.client_id=b.demographic_no
and c.demographic_no=a.client_id
and d.program_id=a.program_id

order by
(select r.name from room r where r.room_id=c.room_id),(select if.intake_head_id from intake_family if where if.intake_id = a.intake_id), a.intake_id
/

prompt
prompt Creating view V_ROOM_BED_COUNT
prompt ==============================
prompt
create or replace view v_room_bed_count as
select program_id,room_id, count(*) as beds
from (
select c.*, b.program_id
from program a, room b, bed c, facility d
where a.facility_id=d.id and a.program_id = b.program_id and b.room_id = c.room_id
and d.active=1 and b.active = 1 and c.active= 1
)
group by program_id,room_id
/

prompt
prompt Creating view V_REP_CAPACITY
prompt ============================
prompt
CREATE OR REPLACE VIEW V_REP_CAPACITY AS
SELECT
a.client_id client_id,
  a.admission_date admission_date,
  a.discharge_date discharge_date,
  a.admission_status,
  b.name program_name,
  b.descr program_description,
  b.max_allowed max_prog_occ,
  b.facility_id,
  f.name facility_name,
  k.beds facility_total_beds,
  c.bed_id bed_id,
  c.name bed_name,
  i.beds prog_total_beds,
  d.room_id room_id,
  d.name room_name,
  d.occupancy max_room_occ,
  j.beds room_total_beds,
  e.last_name last_name,
  e.first_name first_name,
  b.orgCd,
  g.name org_name,
  l.grand_total_bed_count
FROM
  admission a,
  v_program b,
  bed c,
  room d,
  demographic e,
  facility f,
  lst_shelter g,
  --bed_demographic h,
  v_program_bed_count i,
  v_room_bed_count j,
  v_facility_bed_count k,
  v_grand_total_beds l
WHERE
     a.program_id=b.program_id
     and
     a.program_id=d.program_id
     and
     a.program_id=i.program_id
     and
     a.program_id=j.program_id
     and
     b.facility_id=k.facility_id
     and
     d.room_id=j.room_id
     and
     b.facility_id=f.id
     and
     c.room_id=d.room_id
    -- and   c.bed_id=h.bed_id
     and
     a.client_id=e.demographic_no
    -- and   a.client_id=h.demographic_no
     and
     b.shelter_id=g.id
     and
     a.admission_status='current'
/

prompt
prompt Creating view V_REP_CAPACITY_LONG
prompt =================================
prompt
CREATE OR REPLACE VIEW V_REP_CAPACITY_LONG AS
SELECT
a.client_id client_id,
  a.admission_date admission_date,
  a.discharge_date discharge_date,
  a.admission_status,
  b.name program_name,
  b.descr program_description,
  b.max_allowed max_prog_occ,
  b.facility_id,
  f.name facility_name,
  k.beds facility_total_beds,
  c.bed_id bed_id,
  c.name bed_name,
  i.beds prog_total_beds,
  d.room_id room_id,
  d.name room_name,
  d.occupancy max_room_occ,
  j.beds room_total_beds,
  e.last_name last_name,
  e.first_name first_name,
  g.ORGCD,
  g.name org_name,
  l.grand_total_bed_count
FROM
  admission a,
  program b,
  bed c,
  room d,
  demographic e,
  facility f,
  v_program g,
 -- bed_demographic h,
  v_program_bed_count i,
  v_room_bed_count j,
  v_facility_bed_count k,
  v_grand_total_beds l
WHERE
     a.program_id=b.program_id
     and
     a.program_id=d.program_id
     and
     a.program_id=i.program_id
     and
     a.program_id=j.program_id
     and
     b.facility_id=k.facility_id
     and
     d.room_id=j.room_id
     and
     b.facility_id=f.id
     and
     c.room_id=d.room_id
     --and  c.bed_id=h.bed_id
     and
     a.client_id=e.demographic_no
     --and a.client_id=h.demographic_no
     and
     b.program_id=g.program_id
/

prompt
prompt Creating view V_REP_CLIENT
prompt ==========================
prompt
CREATE OR REPLACE VIEW V_REP_CLIENT AS
(SELECT DEMOGRAPHIC.LAST_NAME LAST_NAME,
DEMOGRAPHIC.FIRST_NAME FIRST_NAME,
DEMOGRAPHIC.ADDRESS ADDRESS,
DEMOGRAPHIC.CITY CITY,
DEMOGRAPHIC.PROVINCE PROVINCE,
DEMOGRAPHIC.POSTAL POSTAL,
DEMOGRAPHIC.ALIAS ALIAS,
INTAKE.INTAKE_ID INTAKE_ID,
INTAKE.CLIENT_ID CLIENT_ID,
INTAKE.STAFF_ID STAFF_ID,
INTAKE.CREATION_DATE CREATION_DATE
FROM QUATROSHELTER.INTAKE INNER JOIN DEMOGRAPHIC ON INTAKE.CLIENT_ID=DEMOGRAPHIC.DEMOGRAPHIC_NO )
/

prompt
prompt Creating function F_ORG_SHELTER
prompt ===============================
prompt
CREATE OR REPLACE FUNCTION "F_ORG_SHELTER" /* Display organization level descriptions from level 3
*/

(
   p_orgcd8 varchar2
) RETURN VARCHAR2

IS
	v_orgcd varchar(72);
	v_desc varchar2(1080);
	v_code varchar2(72);
	v_tmp  varchar2(120);
	v_Char1 varchar2(1);
	v_Char2 varchar2(1);
  v_idx1 int;
  v_idx2 int;
	cursor c_ORGCD IS
		select description from lst_orgcd where code=v_code;
BEGIN

  BEGIN
       SELECT FULLCODE INTO v_orgcd
       FROM LST_ORGCD
       WHERE CODE = p_orgcd8;
  EXCEPTION
           WHEN NO_DATA_FOUND THEN RETURN '';
  END;

	v_Char1 := 'S';
	v_Char2 := 'F';
	v_desc := '';

  v_idx1 := INSTR(v_orgCd,v_CHAR1,1);
  v_idx2 := INSTR(v_orgCd,v_CHAR2,1);

  if(v_idx1) > 0 THEN
     if (v_idx2 > 0) THEN
     		v_code := substr(v_orgcd,v_idx1, v_idx2-v_idx1);
     else
     		v_code := substr(v_orgcd,v_idx1);
     end if;


   		Open c_ORGCD;
	  	Fetch c_ORGCD Into v_tmp;
		  If c_ORGCD%NOTFOUND Then
		    null;
		  End if;
		  close c_ORGCD;

		  v_desc := v_desc || v_tmp;
   END iF;
	Return v_desc;
END;
/

prompt
prompt Creating view V_REP_CLIENTSEARCH
prompt ================================
prompt
create or replace view v_rep_clientsearch as
select
a.ID,
a.PROVIDER_NO userno,
b.last_name||', ' ||b.first_name username,
a.DATETIME,
a.ACTION,
a.CONTENTID,
f_org_shelter('P'||substr(a.contentid,instr(a.contentid,'=')+1)) shelter ,
a.CONTENT,
a.ip IP_Address
from
pmm_log a,
provider b
where a.provider_no=b.provider_no
and a.content like '%lient'
/

prompt
prompt Creating view V_REP_CLIENT_PROFILE
prompt ==================================
prompt
create or replace view v_rep_client_profile as
select
a.demographic_no,
a.last_name,
a.first_name,
a.address,
a.city,
a.province,
a.postal,
a.phone,
a.phone2,
a.email,
to_char(a.dob,'YYYY/MM/DD') DOB,
a.roster_status,
a.patient_status,
a.date_joined,
a.sex,
a.alias,
a.previousaddress,
a.children,
a.sourceofincome,
a.citizenship,
b.demographic_no merged_old_id,
c.program_id,
c.admission_date,
c.admission_from_transfer admit_transfer_yn,
c.admission_notes admission_notes,
c.discharge_date,
c.discharge_from_transfer discharge_transfer_yn,
c.discharge_notes,
c.admission_status,
c.dischargereason discharge_reason,
d.name program_name,
d.ORGCD
from
demographic a,
demographic_merged b,
admission c,
v_program d
where
a.demographic_no = c.client_id(+)
and
a.demographic_no=b.merged_to(+)
and c.program_id=d.program_id(+)
/

prompt
prompt Creating view V_REP_CLIENT_PROFILE_BASIC
prompt ========================================
prompt
create or replace view v_rep_client_profile_basic as
select
a.demographic_no client_id,
a.last_name,
a.first_name,
a.alias,
a.DOB,
b.Description GENDERDESC,
a.sex gender,
c.message HS_Message,
(select count(*) from admission where client_id=a.demographic_no and admission_status='admitted') ACTIVE
from
demographic a,
lst_gender b,
health_safety c
where a.sex=b.code
and a.demographic_no=c.demographic_no(+)
/

prompt
prompt Creating view V_REP_COMPLAINT
prompt =============================
prompt
create or replace view v_rep_complaint as
select
a.ID,
a.SOURCE Source_code,
dddd.description Source_desc,
a.METHOD Method_code,
ddddd.description method_desc,
a.FIRSTNAME complaintnat_firstname,
a.LASTNAME complaintnat_lastname,
a.STANDARDSRELATED,
decode(a.STANDARDSRELATED, 1, 'Yes', 0, 'No')Standards_RelatedYN,
a.STANDARDS standards_code,
d.description standards_desc,
a.DESCRIPTION complaint_description,
a.SATISFIED_WITH_OUTCOME satisfied_code,
ddd.description outcome_desc,
decode(a.standards_breached, 1,'Yes', 0,'','','')security_breached,
a.OUTSTANDING_ISSUES,
decode(a.status, 1, 'Complete', 0, 'In Progress')complaint_status,
a.COMPLETED_DATE,
a.CREATED_DATE,
a.DURATION time_spent,
a.PERSON_1,
a.TITLE_1,
a.DATE_1,
a.PERSON_2,
a.TITLE_2,
a.DATE_2,
a.PERSON_3,
a.TITLE_3,
a.DATE_3,
a.PERSON_4,
a.TITLE_4,
a.DATE_4,
a.CLIENT_ID,
a.PROGRAM_ID,
f_org_facility('P'||a.program_id) Program_Name,
f_org_org('P'||a.program_id) Organization_Name,
f_org_shelter('P'||a.program_id) Shelter_Name,
b.type program_type,
bb.sector_id sector_code,
c.description sector_name,
dd.last_name||' '|| dd.first_name client_name
from
complaint a,
PROGRAM b,
FACILITY bb,
LST_SECTOR c,
lst_shelter_standards d,
demographic dd,
lst_complaint_outcome ddd,
lst_complaint_source dddd,
lst_complaint_method ddddd

where
a.client_id=dd.demographic_no
and
a.program_id=b.program_id(+)
and
b.facility_id=bb.id(+)
and
bb.sector_id=c.id(+)
and
a.source=dddd.id(+)
and
a.method=ddddd.id(+)
and
a.satisfied_with_outcome=ddd.id(+)
and
a.standards like '%,'|| to_char(d.id(+))||'%'


/*left outer join lst_shelter_standards d on a.standards like '%,'||d.id||'%',*/
/

prompt
prompt Creating view V_REP_FACILITYMESSAGES
prompt ====================================
prompt
create or replace view v_rep_facilitymessages as
select "ID",
"MESSAGE",
"CREATION_DATE",
"EXPIRY_DATE",
"FACILITY_ID",
"FACILITY_NAME",
a.type,
b.description MESSAGE_TYPE,
b.code FACILITY_CODE,
c.codecsv orgcd
from
facility_message a,
LST_MESSAGE_TYPE b,
Lst_ORGCD c
where
a.type=b.code(+)
and
'F'||a.facility_id=c.code
/

prompt
prompt Creating view V_REP_INCIDENT
prompt ============================
prompt
create or replace view v_rep_incident as
select distinct
a.ID,
a.CREATED_DATE,
a.PROVIDER_NO,
a.INCIDENT_DATE,
a.INCIDENT_TIME,
a.CLIENTS,
a.STAFF,
a.WITNESSES,
a.other_involved others_involved,
--ddd.Description others_involved,
dddd.description INCIDENT_NATURE,
a.LOCATION,
xx.id client_issues, --xx.description CLIENT_ISSUES,
a.DESCRIPTION,
x.description DISPOSITION,
a.RESTRICTION,
decode(a.CHARGES_LAID,'1','Yes','0','No') ChargesLaid,
a.POLICE_REPORT_NO,
a.BADGE_NO,
a.INVESTIGATION_RCMD,
a.INVESTIGATION_CONDUCTEDBY,
a.INVESTIGATION_DATE,
a.FOLLOWUP_INFO,
a.FOLLOWUP_COMPLETEDBY,
a.FOLLOWUP_DATE,
decode(a.REPORT_COMPLETED,'1','Complete','','In Progress')ReportComplete,
a.PROGRAM_ID,
f_org_program('P'||a.program_id) Program_Name,
f_org_facility('P'||a.program_id) Facility_Name,
f_org_org('P'||a.program_id) Organization_Name,
f_org_shelter('P'||a.program_id) Shelter_Name,
b.type program_type,
bb.sector_id sector_code,
c.description sector_name,
d.demographic_no client_id,
d.last_name||' '|| d.first_name client_name,
pp.provider_no staff_id,
pp.last_name||' '||pp.first_name staff_name,
to_char(a.id||d.demographic_no) clientincidentkey,
x.description INCIDENT_DISPOSITION,
x.id INCIDENT_DISPOSITION_CODE,
xx.description INCIDENT_CLIENT_ISSUE,
xx.id INCIDENT_CLIENT_ISSUE_CODE,
b.ORGCD,
a.report_completed signedyn
from
incident a left outer join demographic d on (a.clients like '%:' || d.demographic_no || '%' or a.clients like  d.demographic_no||'/%')
left outer join provider pp on (a.staff like '%:'||pp.provider_no||'%' or a.staff like pp.provider_no||'/%')
left outer join provider dd on a.staff like '%:'||dd.provider_no||'%'
left outer join lst_incident_others ddd on a.other_involved like '%,'||ddd.id||'%'
left outer join lst_incident_nature dddd on a.nature like '%,'||dddd.id||'%'
join V_PROGRAM b on a.program_id=b.program_id
join FACILITY bb on b.facility_id=bb.id
join LST_SECTOR c on bb.sector_id=c.id
left outer join LST_INCIDENT_DISPOSITION x on a.disposition like '%,'||to_char(x.id)||'%'
left outer join LST_INCIDENT_CLIENTISSUES xx on a.client_issues like '%,'||to_char(xx.id)||'%'
/

prompt
prompt Creating view V_REP_INCIDENT_CLIENTS
prompt ====================================
prompt
create or replace view v_rep_incident_clients as
select distinct
a.ID,
b.clientid client_id,
d.last_name||' '|| d.first_name client_name
from
incident a,
incident_client b,
demographic d
where
a.id=b.incidentid
and b.clientid=d.demographic_no
/

prompt
prompt Creating view V_REP_INCIDENT_STAFF
prompt ==================================
prompt
create or replace view v_rep_incident_staff as
select distinct
a.ID,
pp.staffid staff_id,
ppp.last_name||' '||ppp.first_name staff_name
from
incident a,
incident_staff pp,
provider ppp
where
a.id=pp.incidentid
and pp.staffid = ppp.provider_no
/

prompt
prompt Creating view V_REP_INCIDENT_SUMMARY
prompt ====================================
prompt
CREATE OR REPLACE VIEW V_REP_INCIDENT_SUMMARY AS
(SELECT LST_PROGRAM_TYPE.DESCRIPTION DESCRIPTION,
LST_SECTOR.DESCRIPTION DESCRIPTION1,
V_REP_INCIDENT.ID ID,
V_REP_INCIDENT.INCIDENT_DATE INCIDENT_DATE,
V_REP_INCIDENT.INCIDENT_TIME INCIDENT_TIME,
V_REP_INCIDENT.PROGRAM_ID PROGRAM_ID,
V_REP_INCIDENT.ORGANIZATION_NAME ORGANIZATION_NAME,
V_REP_INCIDENT.SHELTER_NAME SHELTER_NAME,
V_REP_INCIDENT.CLIENTS CLIENTS,
LST_PROGRAM_TYPE.DESCRIPTION PROGRAM_TYPE,
LST_PROGRAM_TYPE.CODE PROGRAM_TYPE_CODE
FROM QUATROSHELTER.V_REP_INCIDENT LEFT OUTER JOIN LST_PROGRAM_TYPE ON V_REP_INCIDENT.PROGRAM_TYPE=LST_PROGRAM_TYPE.CODE
 LEFT OUTER JOIN LST_SECTOR ON V_REP_INCIDENT.SECTOR_CODE=LST_SECTOR.ID )
/

prompt
prompt Creating view V_REP_INTAKE
prompt ==========================
prompt
CREATE OR REPLACE VIEW V_REP_INTAKE AS
SELECT distinct
a.INTAKE_ID,
a.REFERREDBY,
a.ABORIGINAL,
a.CURSLEEPARRANGEMENT,
a.LENGTHOFHOMELESS,
f.description REASONFORHOMELESS,
f.code HOMELESS_CODE,
a.SOURCEINCOME,
a.LIVEDBEFORE,
a.STATUSINCANADA,
h.description REFERREDTO,
h.code REFERRED_TO_CODE,
g.description REASONNOADMIT,
g.code REASON_NO_ADMIT_CODE,
a.CONTACTNAME,
a.CONTACTNUMBER,
a.CONTACTEMAIL,
to_char(decode(a.YOUTH, 1,'Y',1,'N','N'))YOUTH,
a.ABORIGINALOTHER,
to_char(decode(a.pregnant,1,'Y',0,'N','N'))PREGNANT,
to_char(decode(a.DISCLOSEDABUSE,1,'Y',0,'N','N'))DISCLOSEDABUSE,
decode(a.DISABILITY,'1','Y','0','N','N')DISABILITY,
to_char(decode(a.observedabuse,1,'Y',0,'N','N'))OBSERVEDABUSE,
to_char(decode(a.disclosedmentalissue,1,'Y',0,'N','N'))DISCLOSEDMENTALISSUE,
to_char(decode(a.poorhygiene,1,'Y',0,'N','N'))POORHYGIENE,
to_char(decode(a.observedmentalissue,1,'Y',0,'N','N'))OBSERVEDMENTALISSUE,
to_char(decode(a.disclosedalcoholabuse,1,'Y',0,'N','N'))DISCLOSEDALCOHOLABUSE,
to_char(decode(a.observedalcoholabuse,1,'Y',0,'N','N'))OBSERVEDALCOHOLABUSE,
a.BIRTHCERTIFICATE,
a.BIRTHCERTIFICATEYN,
a.SIN,
a.SINYN,
a.HEALTHCARDNO,
a.HEALTHCARDNOYN,
a.DRIVERLICENSENO,
a.DRIVERLICENSENOYN,
a.CITIZENCARDNO,
a.CITIZENCARDNOYN,
a.NATIVERESERVENO,
a.NATIVERESERVENOYN,
a.VETERANNO,
a.VETERANNOYN,
a.RECORDLANDING,
a.RECORDLANDINGYN,
a.LIBRARYCARD,
a.LIBRARYCARDYN,
a.IDOTHER,
a.INCOME,
a.INCOMEWORKERNAME1,
a.INCOMEWORKERPHONE1,
a.INCOMEWORKEREMAIL1,
a.INCOMEWORKERNAME2,
a.INCOMEWORKERPHONE2,
a.INCOMEWORKEREMAIL2,
a.INCOMEWORKERNAME3,
a.INCOMEWORKERPHONE3,
a.INCOMEWORKEREMAIL3,
a.LIVEDBEFOREOTHER,
a.ORIGINALCOUNTRY,
a.COMMENTS,
a.LANGUAGE,
to_char(decode(a.VAW,1,'Y',0,'N','N'))VAW,
to_char(decode(a.inshelterbefore,1,'Y',0,'N','N'))INSHELTERBEFORE,
a1.creation_date,
a1.intake_status,
a1.program_id,
c.type program_type,
f_org_program('P'||a1.program_id) program,
f_org_facility('P'||a1.program_id) facility,
f_org_shelter('P'||a1.program_id) shelter,
f_org_org('P'||a1.program_id) organization,
e.description sector,
c.ORGCD
FROM
  intake a1,
  report_intake a,
  v_program c,
  facility d,
  lst_sector e,
  lst_reasonforhomeless f,
  lst_reasonnoadmit g,
  lst_referredto h
WHERE
a1.intake_id = a.intake_id
and
a1.program_id=c.program_id
and
c.facility_id=d.id
and
d.sector_id=e.id(+)
and
a.reasonforhomeless=f.code(+)
and
a.reasonnoadmit=g.code(+)
and
a.referredto=h.code(+)
/

prompt
prompt Creating view V_REP_SYSTEMMESSAGES
prompt ==================================
prompt
create or replace view v_rep_systemmessages as
select "ID",
"MESSAGE",
"CREATION_DATE",
"EXPIRY_DATE",
a.type,
b.description MESSAGE_TYPE
from
system_message a,
LST_MESSAGE_TYPE b
where
a.type=b.code(+)
/

prompt
prompt Creating view V_REP_INTAKEMESSAGE
prompt =================================
prompt
CREATE OR REPLACE VIEW V_REP_INTAKEMESSAGE AS
(SELECT V_REP_FACILITYMESSAGES.MESSAGE_TYPE FACILITY_MESSAGE_TYPE,
V_REP_INTAKE.INTAKE_ID INTAKE_ID,
V_REP_INTAKE.CREATION_DATE CREATION_DATE,
V_REP_INTAKE.INTAKE_STATUS INTAKE_STATUS,
V_REP_SYSTEMMESSAGES.MESSAGE_TYPE SYSTEM_MESSAGE_TYPE
FROM QUATROSHELTER.V_REP_INTAKE INNER JOIN V_REP_FACILITYMESSAGES ON V_REP_INTAKE.CREATION_DATE=V_REP_FACILITYMESSAGES.CREATION_DATE
 INNER JOIN V_REP_SYSTEMMESSAGES ON V_REP_INTAKE.CREATION_DATE=V_REP_SYSTEMMESSAGES.CREATION_DATE )
/

prompt
prompt Creating view V_REP_INTAKE_OLD
prompt ==============================
prompt
CREATE OR REPLACE VIEW V_REP_INTAKE_OLD AS
SELECT
a.client_id client_id,
  a.CREATION_DATE INTAKE_DATE,
  a.INTAKE_STATUS,
  a.STAFF_ID,
  C.LAST_NAME STAFF_LAST_NAME,
  C.FIRST_NAME STAFF_FIRST_NAME,
  C.PROVIDER_TYPE STAFF_TYPE,
  C.PHONE STAFF_PHONE,
  b.name program_name,
  b.descr program_description,
  b.address prog_address,
  b.phone prog_phone,
  b.emergency_number prog_emrg_num,
  b.location prog_location,
  b.max_allowed prog_max_occ,
  b.num_of_members prog_numb_members,
  b.program_status,
  B.FACILITY_ID,
  D.NAME FACILITY_NAME,
  D.DESCRIPTION FACILITY_DESC,
  D.ORG_ID,
  DD.DESCRIPTION ORGANIZATION_NAME,
  D.SECTOR_ID,
  DDD.DESCRIPTION SECTOR,
  f.last_name last_name,
  f.first_name first_name,
  f.address demo_address,
  f.city demo_city,
  f.province demo_prov,
  f.postal demo_postal,
  f.phone demo_phone,
  to_char(f.dob,'YYYYMMDD') DOByyyymmdd
FROM
  INTAKE a,
  PROGRAM b,
  PROVIDER C,
  FACILITY D,
  LST_ORGANIZATION DD,
  LST_SECTOR DDD,
  DEMOGRAPHIC f
WHERE
     a.program_id=b.program_id
     and
     a.Staff_Id=C.PROVIDER_NO
     and
     B.FACILITY_ID=D.ID
     AND
     D.SECTOR_ID=DDD.ID
     AND
     D.ORG_ID=DD.ID
     AND
     a.client_id=f.demographic_no
/

prompt
prompt Creating view V_REP_INTAKE_SUMMARY
prompt ==================================
prompt
create or replace view v_rep_intake_summary as
select
a.intake_id,
a.intake_node_id,
a.client_id,
a.staff_id,
a.creation_date,
a.intake_status,
a.intake_location,
a.program_id,
f_org_facility('P'||a.program_id) Program_Name,
f_org_org('P'||a.program_id) Organization_Name,
f_org_shelter('P'||a.program_id) Shelter_Name,
c.description sector,
b.type program_type
from
INTAKE a,
PROGRAM b,
FACILITY bb,
LST_SECTOR c

where
a.program_id=b.program_id
and
b.facility_id=bb.id
and
bb.sector_id=c.id
/

prompt
prompt Creating view V_REP_INTAKE_SUMMARY_OLD
prompt ======================================
prompt
CREATE OR REPLACE VIEW V_REP_INTAKE_SUMMARY_OLD AS
SELECT
  a.intake_id,
  a.client_id client_id,
  D.LAST_NAME CLIENT_LAST_NAME,
  D.FIRST_NAME CLIENT_FIRST_NAME,
  a.CREATION_DATE INTAKE_DATE,
  a.STAFF_ID,
  E.LAST_NAME||',' ||E.FIRST_NAME STAFF_NAME,
  a.INTAKE_STATUS STATUS,
  A.PROGRAM_ID ORGCD,
  f_org_program('P'||A.PROGRAM_ID) PROGRAM,
  f_org_facility('P'||A.PROGRAM_ID) FACILITY,
  f_org_org('P'||A.PROGRAM_ID) ORGANIZATION
FROM
  INTAKE A,
  LST_ORGCD B,
  DEMOGRAPHIC D,
  PROVIDER E
WHERE
A.CLIENT_ID=D.DEMOGRAPHIC_NO
AND
A.STAFF_ID=E.PROVIDER_NO
and
'P'||a.program_id=b.code(+)
/

prompt
prompt Creating view V_REP_INTAKE_UNION_ADMIT
prompt ======================================
prompt
CREATE OR REPLACE VIEW V_REP_INTAKE_UNION_ADMIT AS
SELECT
intake_id,
NULL ADMISSION_ID,
client_id,
'P' || program_id ORGCD
FROM
V_REP_INTAKE_SUMMARY
UNION
SELECT
intake_id,
ADMISSION_ID,
client_id,
PROGRAM
FROM V_REP_ADMISSION_SUMMARY
/

prompt
prompt Creating view V_REP_INTAKE_VS_ADMIT
prompt ===================================
prompt
CREATE OR REPLACE VIEW V_REP_INTAKE_VS_ADMIT AS
SELECT
A.intake_id,
B.ADMISSION_ID,
A.client_id,
A.Program_Name ORGANIZATION,
A.SECTOR,
A.PROGRAM_TYPE,
B.DISCHARGE_REASON DISCHARGE_REASON
FROM
V_REP_INTAKE_SUMMARY A,
V_REP_ADMISSION_SUMMARY B
WHERE
A.INTAKE_ID = B.INTAKE_ID(+)
/

prompt
prompt Creating view V_REP_LOGIN
prompt =========================
prompt
create or replace view v_rep_login as
select
a.DATETIME,
a.PROVIDER_NO userno,
b.last_name||', ' ||b.first_name username,
a.ACTION,
a.CONTENT,
f_org_shelter('S'||substr(a.contentid,instr(a.contentid,'=')+1)) shelter ,
a.CONTENTID
from
log a,
provider b where a.provider_no=b.provider_no
/

prompt
prompt Creating view V_REP_NONADMISSION
prompt ================================
prompt
CREATE OR REPLACE VIEW V_REP_NONADMISSION AS
SELECT
A.REFERRAL_ID,
A.CLIENT_ID,
D.LAST_NAME CLIENT_LASTNAME,
D.FIRST_NAME CLIENT_FIRSTNAME,
A.REFERRAL_DATE,
A.PROVIDER_NO,
E.LAST_NAME||',' ||E.FIRST_NAME STAFF_NAME,
A.NOTES,
C.NAME PROGRAM_NAME,
C.DESCR PROGRAM_DESCRIPTION,
A.STATUS,
A.COMPLETION_NOTES,
A.COMPLETION_DATE,
A.PRESENT_PROBLEMS,
A.REJECTIONREASON,
C.ORGCD
FROM
CLIENT_REFERRAL A,
LST_ORGCD B,
V_PROGRAM C,
DEMOGRAPHIC D,
PROVIDER E
WHERE
A.CLIENT_ID=D.DEMOGRAPHIC_NO
AND
A.PROVIDER_NO=E.PROVIDER_NO
AND
A.PROGRAM_ID=C.PROGRAM_ID
/

prompt
prompt Creating view V_REP_OCCUPANCY
prompt =============================
prompt
create or replace view v_rep_occupancy as
select distinct
a.occdate,
a.program_id,
b.sector,
a.occupancy,
a.capacity_actual,
a.capacity_funding,
a.queue,
f_org_program('P'||a.program_id) program,
f_org_facility('P'||a.program_id) facility,
f_org_shelter('P'||a.program_id) shelter,
f_org_org('P'||a.program_id) organization,
--g.message facility_message,
--h.message system_message,
e.codecsv orgcd
from
program_occupancy a,
v_prog_sector b,
facility_message c,
--system_message d,
lst_orgcd e
where
a.program_id=b.program_id
and
'P'||a.program_id=e.code
--and
--b.facility_id=f.id
--and
--f.sector_id=c.id
--and
--b.facility_id=g.facility_id(+)
--and
--h.creation_date=a.occdate(+)
/

prompt
prompt Creating function F_GET_PROG_ACTUAL_CAPACITY
prompt ============================================
prompt
CREATE OR REPLACE FUNCTION "F_GET_PROG_ACTUAL_CAPACITY" /* Display organization level descriptions from level 3*/
(
   p_programId IN NUMBER
) RETURN NUMBER
IS
  v_actualCapacity number;
BEGIN
     BEGIN
          select sum(decode(r.assigned_bed,0,r.occupancy,1,count(b.bed_id)))
                 into v_actualCapacity
          from bed b,room r where b.room_id(+)=r.room_id
               and (b.active=1 or b.active is null) and r.active=1
               and r.program_id=p_programId
               group by b.room_id,r.assigned_bed,r.occupancy;
     EXCEPTION
              WHEN OTHERS THEN NULL;
     END;
     return v_actualCapacity;
END;
/

prompt
prompt Creating function F_GET_PROG_QUEUE
prompt ==================================
prompt
CREATE OR REPLACE FUNCTION "F_GET_PROG_QUEUE" /* Display organization level descriptions from level 3*/
(
   p_programId IN NUMBER
) RETURN NUMBER
IS
  v_queue number;
BEGIN
     BEGIN
          select count(*) into v_queue from program_queue pq
          where pq.program_id=p_programId;
     EXCEPTION
              WHEN OTHERS THEN NULL;
     END;
     return v_queue;
END;
/

prompt
prompt Creating view V_REP_REALTIME_OCC
prompt ================================
prompt
create or replace view v_rep_realtime_occ as
select
A.PROGRAM_ID,
COUNT(*) OCCUPANCY,
F_GET_PROG_ACTUAL_CAPACITY(a.program_id) ACTUAL_CAPACITY,
b.capacity_funding,
f_get_prog_queue(a.program_id) queue,
b.name PROGRAM,
f_org_org('P'||a.program_id) ORGANIZATION,
f_org_shelter('P'||a.program_id)SHELTER,
f_org_facility('P'||a.program_id)FACILITY,
c.sector_id SECTOR_CODE,
d.description SECTOR
from
admission a, program b, facility c, lst_sector d
where
a.program_id = b.program_id
and
a.admission_status='admitted'
and
b.facility_id=c.id
and
c.sector_id=d.id
GROUP BY
A.PROGRAM_ID,
b.capacity_funding,
b.name,
c.sector_id,
d.description
/

prompt
prompt Creating function F_GET_SR_STATUS
prompt =================================
prompt
CREATE OR REPLACE FUNCTION F_GET_SR_STATUS(p_id in NUMBER) /*get service restriction stattus*/
return varchar2 
IS
			status varchar2(20);
      EarlyTerminationProvider PROGRAM_CLIENT_RESTRICTION.EARLY_TERMINATION_PROVIDER%TYPE;
      startDate PROGRAM_CLIENT_RESTRICTION.Start_Date%Type;
      endDate PROGRAM_CLIENT_RESTRICTION.End_Date%Type;
      now Date;
BEGIN
      now := sysdate;
      BEGIN
            SELECT a.early_termination_provider, a.start_date, a.end_date INTO EarlyTerminationProvider, startDate, endDate
            FROM  PROGRAM_CLIENT_RESTRICTION a
            WHERE ID=p_id;
      EXCEPTION
            WHEN OTHERS THEN NULL;
      END;

			if (NOT EarlyTerminationProvider IS null) THEN
					status := 'terminated early';
			elsif (endDate < now) THEN
					status := 'completed';
			elsif (startDate <= now and endDate >= now) THEN
					status := 'in progress';
      end if;
      return status;
 END;
/

prompt
prompt Creating view V_REP_SERVICERESTRICTION
prompt ======================================
prompt
create or replace view v_rep_servicerestriction as
select
a.ID,
a.PROGRAM_ID,
d.name program_name,
f_org_facility('P'||a.program_id) facility_name,
f_org_shelter('P'||a.program_id) shelter_name,
f_org_org('P'||a.program_id) organization_name,
a.DEMOGRAPHIC_NO client_id,
b.last_name||', '||b.first_name client_name,
a.PROVIDER_NO staff_id,
e.last_name||', '||e.first_name staff_name,
a.comments RESTRICTION_CODE,
c.description RESTRICTION_REASON,
F_GET_SR_sTATUS(A.ID) status,
a.START_DATE,
a.END_DATE,
a.EARLY_TERMINATION_PROVIDER,
a.LASTUPDATEDATE,
a.NOTES,
d.ORGCD
from
program_client_restriction a,
demographic b,
lst_service_restriction c,
v_program d,
provider e
where
a.program_id=d.program_id
and
a.demographic_no=b.demographic_no
and
a.comments=c.id
and
a.provider_no=e.provider_no
/

prompt
prompt Creating view V_REP_USERACTIONS
prompt ===============================
prompt
create or replace view v_rep_useractions as
select
a.ID,
a.PROVIDER_NO userno,
c.user_name userid,
b.last_name||', ' ||b.first_name username,
a.DATETIME,
a.ACTION,
a.CONTENTID,
f_org_shelter('P'||substr(a.contentid,instr(a.contentid,'=')+1)) shelter ,
a.CONTENT,
a.ip IP_Address
from
pmm_log a,
provider b,
security c
where a.provider_no=b.provider_no
and a.provider_no = c.provider_no
/

prompt
prompt Creating view V_REP_USERLIST
prompt ============================
prompt
CREATE OR REPLACE VIEW V_REP_USERLIST AS
SELECT DISTINCT
A.PROVIDER_NO USERNO,
C.USER_NAME USERNAME,
A.LAST_NAME LAST_NAME,
A.FIRST_NAME FIRST_NAME,
A.STATUS,
B.ROLE_NAME ROLE_NAME,
A.EMAIL
FROM
PROVIDER A,
SECUSERROLE B,
SECURITY C
WHERE
A.PROVIDER_NO=B.PROVIDER_NO
AND
A.PROVIDER_NO=C.PROVIDER_NO
/

prompt
prompt Creating view V_ROOM_BEDS
prompt =========================
prompt
create or replace view v_room_beds as
select r.room_id,r.program_id,r.facility_id,decode(r.assigned_bed,0,r.occupancy,1,b.beds0) beds
from room r, (select bd.room_id,count(*) beds0 from bed bd where bd.active=1 group by bd.room_id) b
where r.room_id = b.room_id(+) and r.active=1
/

prompt
prompt Creating view V_USER_ACCESS
prompt ===========================
prompt
create or replace view v_user_access as
select a.provider_no, c.code orgcd, c.codecsv orgcdcsv, b.objectname,d.orgapplicable, max(b.privilege) privilege
from secuserrole a, secobjprivilege b, lst_orgcd c, secobjectname d,secrole e
where a.role_name=e.role_name and
a.role_name = b.roleusergroup
and a.orgcd = c.code and b.objectname=d.objectname
and a.activeyn=1 and c.activeyn=1 and e.isactive=1
group by a.provider_no, c.code,c.codecsv, b.objectname,d.orgapplicable
/

prompt
prompt Creating view V_USER_ORGS
prompt =========================
prompt
create or replace view v_user_orgs as
select a.code from lst_orgcd a, secuserrole b
  where a.codetree like '%' || b.orgcd || '%'
/

prompt
prompt Creating view V_USER_REPORT
prompt ===========================
prompt
CREATE OR REPLACE VIEW V_USER_REPORT AS
SELECT     a.REPORTNO, a.TITLE, a.DESCRIPTION, a.ORGAPPLICABLE, a.REPORTTYPE, a.DATEOPTION, a.DATEPART,
           a.REPORTGROUP,d.ID REPORTGROUPID, a.TABLENAME,
           a.NOTES, c.PROVIDER_NO,
           MAX(b.ACCESS_TYPE) ACCESS_TYPE, d.DESCRIPTION REPORTGROUPDESC, a.UPDATEDBY, a.UPDATEDDATE
FROM        REPORT a,REPORT_ROLE b,
            SECUSERROLE c ,
            REPORT_LK_REPORTGROUP d
 where  a.REPORTNO = b.REPORTNO and  b.ROLECODE = c.ROLE_NAME and a.REPORTGROUP(+)= d.ID
GROUP BY a.REPORTNO, a.TITLE, a.DESCRIPTION, a.ORGAPPLICABLE, a.REPORTTYPE, a.DATEOPTION, a.DATEPART, a.REPORTGROUP,d.ID, a.TABLENAME,
                      a.NOTES, c.PROVIDER_NO, d.DESCRIPTION, a.UPDATEDBY, a.UPDATEDDATE
/

prompt
prompt Creating function F_GETREPORTFIELDTYPE
prompt ======================================
prompt
CREATE OR REPLACE FUNCTION F_GetReportFieldType (p_reportNo NUMBER, p_fieldName VARCHAR2)
RETURN varchar2 AS
ret varchar(1);
v_TABLE VARCHAR2(32);
v_ViewNo NUMBER;
BEGIN

     BEGIN
     SELECT TABLENAME INTO v_table
     FROM REPORT
     WHERE REPORTNO  = p_reportNo;

     SELECT QGVIEWNO INTO v_ViewNO
     FROM REPORT_QGVIEWSUMMARY
     WHERE QGVIEWCODE = v_table;

     SELECT FIELDTYPECODE INTO ret
     FROM REPORT_QGVIEWFIELD
     WHERE QGVIEWNO = v_ViewNo and FIELDNAME = p_fieldName;

     EXCEPTION
      WHEN OTHERS THEN ret := 'S';
      END;
	return ret;
END;
/

prompt
prompt Creating function F_IN
prompt ======================
prompt
create or replace function F_IN(p_fieldVal IN varchar2, p_searchVal IN varchar2) return varchar2 is
  Result varchar2(1);
  idx int;
  v varchar2(1024);
  c varchar2(20);
begin
  Result := '0';
  v := p_searchVal;
  if (v is null or p_fieldVal is null) then goto finished; end if;
  
  if (instr(p_fieldVal, ',') > 0) Then
    idx := instr(v,',');
    while (idx > 0) Loop
        c := substr(v,1,idx-1);
        if (p_fieldVal like '%,'  || c || '%') Then
           Result := '1';
           goto finished;
        end if;
        v := substr(v,idx+1);
      idx := instr(v,',');
    end loop;
    if(p_fieldVal like '%,' || v || '%') Then Result := '1'; end if; 
  else
    if (p_searchVal like '%' || p_fieldVal || '%') Then Result := '1'; end if;
  end if;  
  
<<finished>>     
  return(Result);
end F_IN;
/

prompt
prompt Creating function F_ORG_FULLCODE
prompt ================================
prompt
CREATE OR REPLACE FUNCTION F_ORG_FULLCODE
(
   p_orgcd8  varchar2
) RETURN VARCHAR2
IS
v_orgcd varchar2(72);
BEGIN
     SELECT FULLCODE INTO v_orgcd
     FROM LST_ORGCD
     WHERE CODE = p_orgcd8;
   	 Return v_orgcd;
EXCEPTION
         WHEN NO_DATA_FOUND THEN RETURN '';
END;
/

prompt
prompt Creating function LEFT
prompt ======================
prompt
create or replace function LEFT(
       p_str varchar2,
       p_len NUMBER
 ) return varchar2 is
  Result varchar2(4000);
begin
  IF NOT p_str IS NULL THEN
      RESULT := SUBSTR(p_str,1,p_len);
  END IF;
  return(Result);
end LEFT;
/

prompt
prompt Creating function NOW
prompt =====================
prompt
create or replace function NOW
return DATE is
  Result DATE;
begin
  Result := SYSDATE;
  return(Result);
end NOW;
/

prompt
prompt Creating function TO_DAYS
prompt =========================
prompt
create or replace function TO_DAYS(
       p_date Date
 ) return NUMBER is
  Result NUMBER;
  d1 DATE;
begin
  IF NOT p_date IS NULL THEN
     d1 := TO_DATE('19000101','YYYYMMDD');
     RESULT := trunc(p_date) - d1;
  END IF;
  return(Result);
end TO_DAYS;
/

prompt
prompt Creating procedure SP_DAILY_PROGRAM_OCCUPANCY_U
prompt ===============================================
prompt
CREATE OR REPLACE PROCEDURE SP_DAILY_PROGRAM_OCCUPANCY_U
AS
 v_programId                program.program_id%type;
 v_occupancy                program_occupancy.occupancy%type;
 v_actualCapacity           program_occupancy.capacity_actual%type;
 v_fundingCapacity          program_occupancy.capacity_funding%type;
 v_queue                    program_occupancy.queue%type;

 CURSOR c_admission is
     select A.PROGRAM_ID,COUNT(*) from admission a
     where a.admission_status='admitted' GROUP BY A.PROGRAM_ID;
BEGIN

  OPEN c_admission;
      FETCH c_admission INTO v_programId, v_occupancy;
      WHILE c_admission%FOUND
      LOOP
          select sum(decode(r.assigned_bed,0,r.occupancy,1,count(b.bed_id)))
                 into v_actualCapacity
          from bed b,room r where b.room_id(+)=r.room_id
               and (b.active=1 or b.active is null) and r.active=1
               and r.program_id=v_programId
               group by b.room_id,r.assigned_bed,r.occupancy;
          select count(*) into v_queue from program_queue pq
          where pq.program_id=v_programId;

          select p.capacity_funding into v_fundingCapacity
          from program p where p.program_id=v_programId;

          insert into program_occupancy(recordid,occdate,program_id,occupancy,
                  capacity_actual,capacity_funding,queue,lastupdateuser)
           select seq_program_occupancy.nextval,sysdate,v_programId,v_occupancy,
           v_actualCapacity,v_fundingCapacity,v_queue,'2001' from dual;
      FETCH c_admission INTO v_programId, v_occupancy;
      end loop;
      CLOSE c_admission;
END SP_DAILY_PROGRAM_OCCUPANCY_U;
/

prompt
prompt Creating procedure SP_LOOKUPTABLES_I
prompt ====================================
prompt
CREATE OR REPLACE PROCEDURE "SP_LOOKUPTABLES_I"
(
  p_cateId varchar2,
  p_tableid IN OUT varchar2,
	p_tablename IN OUT varchar2,
  p_description varchar2
)
AS
 v_count NUMBER;
 v_tableid varchar2(32);
BEGIN
  p_tableId := upper(p_tableid);
  p_tablename := lower(p_tablename);

  BEGIN
       SELECT TABLEID INTO v_tableid
       FROM APP_LOOKUPTABLE
       WHERE TABLE_NAME=p_tablename;

       dbms_output.PUT_LINE ('The TABLE ALREADY BEEN ADDED wih tableid:'  || v_tableid || ', Please remove it and try again');
       return;
  EXCEPTION
       WHEN OTHERS THEN NULL;
  END;

  SELECT COUNT(*) INTO v_count
  FROM APP_LOOKUPTABLE
  WHERE TABLEID=p_tableid;

  IF (v_count > 0) THEN
     dbms_output.PUT_LINE ('The TABLE ID ALREADY BEEN USED, Please change to another one and try again');
     return;
  END IF;

	INSERT INTO APP_LOOKUPTABLE
	(TABLEID,MODULEID,TABLE_NAME,DESCRIPTION,ISTREE,TREECODE_LENGTH,ACTIVEYN)
	VALUES (upper(p_tableid),p_cateid,lower(p_tablename), p_description,'0',0,'1');

	INSERT INTO APP_LOOKUPTABLE_FIELDS
	(TABLEID,FIELDNAME,FIELDDESC,EDITYN,FIELDTYPE,FIELDSQL,FIELDINDEX,UNIQUEYN,GENERICIDX,AUTOYN)
	SELECT p_tableId, lower(b.COLUMN_NAME),b.COLUMN_NAME, '1',DECODE(b.DATA_TYPE,'VARCHAR2','S','DATE','D','N'),
  lower(b.COLUMN_NAME),b.COLUMN_ID,0,b.COLUMN_ID,0
	FROM ALL_OBJECTS a, USER_TAB_COLUMNS b
  WHERE a.OBJECT_NAME = b.TABLE_NAME
  AND a.object_type IN ('VIEW','TABLE')
	AND a.Object_name = upper(p_tablename);

  UPDATE APP_LOOKUPTABLE_FIELDS SET AUTOYN=1, EDITYN=0
  WHERE TABLEID=p_tableid and fieldname='id';


EXCEPTION
         WHEN OTHERS THEN
              dbms_output.PUT_LINE ('FAILED to add the TABLE, the TABLE ID MAY ALREADY BEEN USED');
END;
/

prompt
prompt Creating procedure SP_QGVIEW_OBJECTS_I
prompt ======================================
prompt
CREATE OR REPLACE PROCEDURE "SP_QGVIEW_OBJECTS_I"
(
	p_name varchar2
)
AS
	v_QGVIEWNO integer;

BEGIN

  BEGIN
	SELECT QGVIEWNO INTO v_QGVIEWNO
	FROM  REPORT_QGVIEWSUMMARY
	where qgviewcode = p_name;
  EXCEPTION
           WHEN OTHERS THEN
                v_QGVIEWNO := 0;
  END;

	IF (v_QGVIEWNO > 0)  THEN
		DELETE REPORT_QGVIEWSUMMARY
		where qgviewcode = p_name;

		DELETE REPORT_QGVIEWFIELD
		where qgviewno= v_QGVIEWNO;
	END IF;

	SELECT HIBERNATE_SEQUENCE.NEXTVAL INTO v_QGVIEWNO
  FROM DUAL;

	INSERT INTO REPORT_QGVIEWSUMMARY
	(QGVIEWNO,QGVIEWCODE, GROUPCODE, MASTERTYPE, UPDATEDBY, UPDATEDDATE, NOTE, ACTIVEYN, SECUREYN, DBENTITY, REFVIEWS, OBJECT_TYPE)
	SELECT v_QGVIEWNO,OBJECT_NAME, '90','M','2001',SYSDATE, 'intial setup','1','0',OBJECT_name,OBJECT_name,OBJECT_TYPE
	FROM ALL_OBJECTS
	where object_name = p_name AND object_type in ('TABLE','VIEW');

	INSERT INTO REPORT_QGVIEWFIELD
	(QGVIEWNO,FIELDNAME, FIELDNO, FIELDTYPECODE,SOURCETXT, NOTE)
	SELECT v_QGVIEWNO, b.COLUMN_NAME, b.COLUMN_ID,
  DECODE(b.DATA_TYPE,'VARCHAR2','S','DATE','D','N'), b.COLUMN_NAME, 'initial set up'
	FROM ALL_OBJECTS a, USER_TAB_COLUMNS b
  WHERE a.OBJECT_NAME = b.TABLE_NAME
  AND a.object_type IN ('VIEW','TABLE')
	AND a.Object_name = p_name;

END;
/

prompt
prompt Creating trigger TRI_ADMISSION
prompt ==============================
prompt
create or replace trigger "TRI_ADMISSION"
after UPDATE or delete ON ADMISSION
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.ADMISSION_LOG VALUES
      (
      :old.AM_ID,
      :old.CLIENT_ID,
      :old.PROGRAM_ID,
      :old.PROVIDER_NO,
      :old.ADMISSION_DATE,
      :old.ADMISSION_NOTES,
      :old.TEMP_ADMISSION,
      :old.DISCHARGE_DATE,
      :old.DISCHARGE_NOTES,
      :old.TEMP_ADMIT_DISCHARGE,
      :old.ADMISSION_STATUS,
      :old.TEAM_ID,
      :old.TEMPORARY_ADMISSION_FLAG,
      :old.AGENCY_ID,
      :old.CLIENTSTATUS_ID,
      :old.DISCHARGEREASON,
      :old.ADMISSION_FROM_TRANSFER,
      :old.DISCHARGE_FROM_TRANSFER,
      :old.AUTOMATIC_DISCHARGE,
      :old.FACILITY_ID,
      :old.INTAKE_ID,
      :old.COMMUNITYPROGRAMCODE,
      :old.TRANSPORTATIONTYPE,
      :old.RESIDENTSTATUS,
      :old.PRIMARYWORKER,
      :old.LOCKERNO,
      :old.NOOFBAGS,
      :old.NEXTKIN_NAME,
      :old.NEXTKIN_RELATIONSHIP,
      :old.NEXTKIN_TELEPHONE,
      :old.NEXTKIN_NUMBER,
      :old.NEXTKIN_STREET,
      :old.NEXTKIN_CITY,
      :old.NEXTKIN_PROVINCE,
      :old.NEXTKIN_POSTAL,
      :old.OVPASS_STARTDATE,
      :old.OVPASS_ENDDATE,
      :old.ISSUEDBY,
      :old.NOTSIGN_REASON,
      :old.LASTUPDATE_DATE,
      :old.LASTUPDATE_USERID,
      QUATROSHELTER_LOG.SEQ_ADMISSION_LOG.NEXTVAL,
      v_action,
      :old.NEXTKIN_COUNTRY
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_ADMISSION;
/

prompt
prompt Creating trigger TRI_AGENCY
prompt ===========================
prompt
create or replace trigger "TRI_AGENCY"
after UPDATE or delete ON AGENCY
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.AGENCY_LOG VALUES
      (
      :old.id,
      :old.intake_quick,
      :old.intake_quick_state,
      :old.intake_indepth,
      :old.intake_indepth_state,
      :old.name,
      :old.description,
      :old.contact_name,
      :old.contact_email,
      :old.contact_phone,
      :old.local,
      :old.integrator_enabled,
      :old.integrator_url,
      :old.integrator_jms,
      :old.integrator_username,
      :old.integrator_password,
      :old.hic,
      :old.share_notes,
      :old.lastupdateuser,
      :old.lastupdatedate,
      QUATROSHELTER_LOG.SEQ_AGENCY_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_AGENCY;
/

prompt
prompt Creating trigger TRI_APP_MODULE
prompt ===============================
prompt
create or replace trigger "TRI_APP_MODULE"
after UPDATE or delete ON APP_MODULE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.APP_MODULE_LOG VALUES
      (
      :old.module_id,
      :old.description,
      :old.isactive,
      :old.displayorder,
      :old.lastupdateuser,
      :old.lastupdatedate,
      QUATROSHELTER_LOG.SEQ_APP_MODULE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_APP_MODULE;
/

prompt
prompt Creating trigger TRI_ATTACHMENT
prompt ===============================
prompt
create or replace trigger "TRI_ATTACHMENT"
after update or delete ON ATTACHMENT
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
    INSERT INTO QUATROSHELTER_LOG.ATTACHMENT_LOG VALUES
    (
    :old.DOCID,
    :old.SUBJECT,
    :old.PRIVACYCD,
    :old.PROVIDERNO,
    :old.CHECKOUT,
    :old.CHECKOUTUSERID,
    :old.CHECKOUTDATE,
    :old.DOCTYPE,
    :old.FILENAME,
    :old.MODULEID,
    :old.REFNO,
    :old.REFPROGRAMID,
    :old.FILETYPE,
    :old.VIEWID,
    :old.VIEWREFNO,
    :old.REVDATETIME,
    quatroshelter_log.Seq_Attachment_Log.NEXTVAL,
    v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_ATTACHMENT;
/

prompt
prompt Creating trigger TRI_BED
prompt ========================
prompt
create or replace trigger "TRI_BED"
after UPDATE or delete ON BED
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.BED_LOG  VALUES
      (
	  :old.BED_ID,
	  :old.BED_TYPE_ID,
	  :old.ROOM_ID,
	  :old.ROOM_START,
	  :old.TEAM_ID,
	  :old.NAME,
	  :old.ACTIVE,
	  :old.LASTUPDATEUSER,
	  :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_BED_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_BED;
/

prompt
prompt Creating trigger TRI_BED_CHECK_TIME
prompt ===================================
prompt
create or replace trigger "TRI_BED_CHECK_TIME"
after UPDATE or delete ON BED_CHECK_TIME
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.bed_check_time_log VALUES
      (
      :old.bed_check_time_id,
      :old.program_id,
      :old.bed_check_time,
      :old.lastupdateuser,
      :old.lastupdatedate,
      QUATROSHELTER_LOG.SEQ_BED_CHECK_TIME_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_BED_CHECK_TIME;
/

prompt
prompt Creating trigger TRI_CASEMGMT_ISSUE_LOG
prompt =======================================
prompt
create or replace trigger "TRI_CASEMGMT_ISSUE_LOG"
after UPDATE or delete ON CASEMGMT_ISSUE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.CASEMGMT_ISSUE_LOG  VALUES
      (
	  :old.ID,
	  :old.DEMOGRAPHIC_NO,
	  :old.ISSUE_ID,
	  :old.ACUTE,
	  :old.CERTAIN,
	  :old.MAJOR,
	  :old.RESOLVED,
	  :old.PROGRAM_ID,
	  :old.TYPE,
	  :old.UPDATE_DATE,
	  :old.LASTUPDATEUSER,
      QUATROSHELTER_LOG.SEQ_CASEMGMT_ISSUE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_CASEMGMT_ISSUE_LOG;
/

prompt
prompt Creating trigger TRI_CASEMGMT_NOTE
prompt ==================================
prompt
create or replace trigger "TRI_CASEMGMT_NOTE"
after update or delete ON CASEMGMT_NOTE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.CASEMGMT_NOTE_LOG VALUES
    (
      :old.NOTE_ID,
      :old.UPDATE_DATE,
      :old.OBSERVATION_DATE,
      :old.DEMOGRAPHIC_NO,
      :old.PROVIDER_NO,
      :old.NOTE,
      :old.SIGNED,
      :old.INCLUDE_ISSUE_INNOTE,
      :old.SIGNING_PROVIDER_NO,
      :old.ENCOUNTER_TYPE,
      :old.BILLING_CODE,
      :old.PROGRAM_NO,
      :old.AGENCY_NO,
      :old.REPORTER_CAISI_ROLE,
      :old.REPORTER_PROGRAM_TEAM,
      :old.HISTORY,
      :old.PASSWORD,
      :old.LOCKED,
      :old.UUID,
      :old.CASESTATUSID,
    quatroshelter_log.seq_casemgmt_note_log.NEXTVAL,
    v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_CASEMGMT_NOTE;
/

prompt
prompt Creating trigger TRI_CLIENT_IMAGE
prompt =================================
prompt
create or replace trigger "TRI_CLIENT_IMAGE"
after update or delete ON CLIENT_IMAGE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.CLIENT_IMAGE_LOG VALUES
    (
    :old.IMAGE_ID,
    :old.DEMOGRAPHIC_NO,
    :old.IMAGE_TYPE,
    :old.IMAGE_DATA,
    :old.UPDATE_DATE,
    :old.LASTUPDATEUSER,
    quatroshelter_log.seq_client_image_log.NEXTVAL,
    v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_CLIENT_IMAGE;
/

prompt
prompt Creating trigger TRI_CLIENT_REFERRAL
prompt ====================================
prompt
create or replace trigger "TRI_CLIENT_REFERRAL"
after update or delete ON CLIENT_REFERRAL
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.CLIENT_REFERRAL_LOG VALUES
    (
    :old.REFERRAL_ID,
    :old.CLIENT_ID,
    :old.REFERRAL_DATE,
    :old.PROVIDER_NO,
    :old.NOTES,
    :old.PROGRAM_ID,
    :old.STATUS,
    :old.COMPLETION_NOTES,
    :old.COMPLETION_DATE,
    :old.PRESENT_PROBLEMS,
    :old.REJECTIONREASON,
    quatroshelter_log.seq_client_referral_log.NEXTVAL,
    v_action,
    :old.AUTOMANUAL,
    :old.FROMPROGRAMID,
    :old.intake_id
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_CLIENT_REFERRAL;
/

prompt
prompt Creating trigger TRI_COMPLAINT
prompt ==============================
prompt
create or replace trigger "TRI_COMPLAINT"
after update or delete ON COMPLAINT
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
    INSERT INTO QUATROSHELTER_LOG.COMPLAINT_LOG VALUES
    (
    :old.ID,
    :old.SOURCE,
    :old.METHOD,
    :old.FIRSTNAME,
    :old.LASTNAME,
    :old.STANDARDS,
    :old.DESCRIPTION,
    :old.SATISFIED_WITH_OUTCOME,
    :old.STANDARDS_BREACHED,
    :old.OUTSTANDING_ISSUES,
    :old.STATUS,
    :old.COMPLETED_DATE,
    :old.CREATED_DATE,
    :old.DURATION,
    :old.PERSON_1,
    :old.TITLE_1,
    :old.DATE_1,
    :old.PERSON_2,
    :old.TITLE_2,
    :old.DATE_2,
    :old.PERSON_3,
    :old.TITLE_3,
    :old.DATE_3,
    :old.PERSON_4,
    :old.TITLE_4,
    :old.DATE_4,
    :old.CLIENT_ID,
    :old.PROGRAM_ID,
    :old.STANDARDSRELATED,
    :old.LASTUPDATEUSER,
    :old.LASTUPDATEDATE,
    quatroshelter_log.seq_complaint_log.NEXTVAL,
    v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_COMPLAINT;
/

prompt
prompt Creating trigger TRI_CONSENT_DETAIL
prompt ===================================
prompt
create or replace trigger "TRI_CONSENT_DETAIL"
after update or delete ON CONSENT_DETAIL
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.CONSENT_DETAIL_LOG VALUES
    (
    :old.ID,
    :old.DEMOGRAPHIC_NO,
    :old.PROVIDER_NO,
    :old.NOTES,
    :old.AGENCYNAME,
    :old.CONTACTNAME,
    :old.CONTACTTITLE,
    :old.CONTACTPHONE,
    :old.STATEPURPOSE,
    :old.STARTDATE,
    :old.ENDDATE,
    :old.DATE_SIGNED,
    :old.STATUS,
    :old.HARDCOPY,
    :old.FORM_NAME,
    :old.FORM_VERSION,
    :old.PROGRAM_ID,
    :old.LASTUPDATEDATE,
    quatroshelter_log.seq_consent_detail_log.NEXTVAL,
    v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_CONSENT_DETAIL;
/

prompt
prompt Creating trigger TRI_DEMOGRAPHIC
prompt ================================
prompt
create or replace trigger "TRI_DEMOGRAPHIC"
after update or delete ON QUATROSHELTER.DEMOGRAPHIC
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.DEMOGRAPHIC_LOG VALUES
    (
    :old.demographic_no,
    :old.last_name,
    :old.first_name,
    :old.address,
    :old.city,
    :old.province,
    :old.postal,
    :old.phone,
    :old.phone2,
    :old.email,
    :old.pin,
    :old.hin,
    :old.ver,
    :old.roster_status,
    :old.patient_status,
    :old.date_joined,
    :old.chart_no,
    :old.provider_no,
    :old.sex,
    :old.end_date,
    :old.eff_date,
    :old.pcn_indicator,
    :old.hc_type,
    :old.hc_renew_date,
    :old.family_doctor,
    :old.alias,
    :old.previousaddress,
    :old.children,
    :old.sourceofincome,
    :old.citizenship,
    :old.sin,
    :old.ismerged,
    :old.benefit_unit_status,
    :old.lastupdatedate,
    quatroshelter_log.seq_demographic_log.NEXTVAL,
    v_action,
    :old.dob
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_DEMOGRAPHIC;
/

prompt
prompt Creating trigger TRI_DEMOGRAPHIC_DEMOGRAPHIC_NO
prompt ===============================================
prompt
CREATE OR REPLACE TRIGGER TRI_DEMOGRAPHIC_DEMOGRAPHIC_NO
BEFORE INSERT
ON DEMOGRAPHIC
FOR EACH ROW
DECLARE
       IDX NUMBER;
BEGIN
     IF :new.DEMOGRAPHIC_NO IS NULL THEN
          SELECT HIBERNATE_SEQUENCE.NEXTVAL INTO IDX FROM DUAL;
          :new.DEMOGRAPHIC_NO := IDX;
     END IF;
END;
/

prompt
prompt Creating trigger TRI_DEMOGRAPHIC_MERGED
prompt =======================================
prompt
create or replace trigger "TRI_DEMOGRAPHIC_MERGED"
after UPDATE or delete ON DEMOGRAPHIC_MERGED
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.DEMOGRAPHIC_MERGED_LOG  VALUES
      (
	    :old.ID,
	    :old.DEMOGRAPHIC_NO,
	    :old.MERGED_TO,
      :old.DELETED,
	    :old.LASTUPDATEUSER,
	    :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_DEMOGRAPHIC_MERGED_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_DEMOGRAPHIC_MERGED;
/

prompt
prompt Creating trigger TRI_DEMOGRAPHIC_MERGED_ID
prompt ==========================================
prompt
CREATE OR REPLACE TRIGGER TRI_DEMOGRAPHIC_MERGED_ID
BEFORE INSERT
ON DEMOGRAPHIC_MERGED
FOR EACH ROW
DECLARE
       IDX NUMBER;
BEGIN
     IF :new.ID IS NULL THEN
          SELECT HIBERNATE_SEQUENCE.NEXTVAL INTO IDX FROM DUAL;
          :new.ID := IDX;
     END IF;
END;
/

prompt
prompt Creating trigger TRI_ECHART
prompt ===========================
prompt
create or replace trigger "TRI_ECHART"
after UPDATE or delete ON ECHART
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.ECHART_LOG  VALUES
      (
	    :old.ECHARTID,
      :old.lastupdatedate,
      :old.DEMOGRAPHICNO,
      :old.PROVIDERNO,
      :old.SUBJECT,
      :old.SOCIALHISTORY,
      :old.FAMILYHISTORY,
      :old.MEDICALHISTORY,
      :old.ONGOINGCONCERNS,
      :old.REMINDERS,
      :old.ENCOUNTER ,
      QUATROSHELTER_LOG.SEQ_ECHART_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_ECHART;
/

prompt
prompt Creating trigger TRI_FACILITY
prompt =============================
prompt
create or replace trigger "TRI_FACILITY"
after UPDATE or delete ON FACILITY
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.FACILITY_LOG  VALUES
      (
	    :old.ID,
      :old.NAME,
      :old.DESCRIPTION,
      :old.CONTACT_NAME,
      :old.CONTACT_EMAIL,
      :old.CONTACT_PHONE,
      :old.HIC,
      :old.ACTIVE,
      :old.ORG_ID,
      :old.SECTOR_ID,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_FACILITY_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_FACILITY;
/

prompt
prompt Creating trigger TRI_FACILITY_DEACTIVE
prompt ======================================
prompt
create or replace trigger "TRI_FACILITY_DEACTIVE"
after update OF ACTIVE ON FACILITY
REFERENCING NEW AS NEW OLD AS OLD
for each row
BEGIN
  BEGIN
    IF :new.ACTIVE = 0 THEN
      UPDATE PROGRAM
      SET PROGRAM_STATUS='0'
      WHERE FACILITY_ID = :new.ID
      ;
    END IF;
  EXCEPTION
     WHEN OTHERS THEN NULL;
 END;
END "TRI_LST_FACILITY_DEACTIVE";
/

prompt
prompt Creating trigger TRI_FACILITY_MESSAGE
prompt =====================================
prompt
create or replace trigger "TRI_FACILITY_MESSAGE"
after UPDATE or delete ON FACILITY_MESSAGE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.FACILITY_MESSAGE_LOG  VALUES
      (
	    :old.ID,
      :old.MESSAGE,
      :old.CREATION_DATE,
      :old.EXPIRY_DATE,
      :old.FACILITY_ID,
      :old.FACILITY_NAME,
      :old.TYPE,
      :old.LASTUPDATEUSER,
      QUATROSHELTER_LOG.SEQ_FACILITY_MESSAGE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_FACILITY_MESSAGE;
/

prompt
prompt Creating trigger TRI_FACILITY_SHELTER
prompt =====================================
prompt
create or replace trigger TRI_FACILITY_SHELTER
  after update of org_id on facility  
  for each row
declare
begin
  update program set shelter_id = :new.org_id
  where facility_id = :new.id;
end TRI_FACILITY_SHELTER;
/

prompt
prompt Creating trigger TRI_HEALTH_SAFETY
prompt ==================================
prompt
create or replace trigger "TRI_HEALTH_SAFETY"
after UPDATE or delete ON HEALTH_SAFETY
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.HEALTH_SAFETY_LOG  VALUES
      (
	    :old.ID,
      :old.DEMOGRAPHIC_NO,
      :old.MESSAGE,
      :old.USERNAME,
      :old.UPDATEDATE,
      QUATROSHELTER_LOG.SEQ_HEALTH_SAFETY_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_HEALTH_SAFETY;
/

prompt
prompt Creating trigger TRI_INCIDENT
prompt =============================
prompt
create or replace trigger "TRI_INCIDENT"
after UPDATE or delete ON INCIDENT
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.INCIDENT_LOG  VALUES
      (
	    :old.ID,
      :old.CREATED_DATE,
      :old.PROVIDER_NO,
      :old.INCIDENT_DATE,
      :old.INCIDENT_TIME,
      :old.CLIENTS,
      :old.STAFF,
      :old.WITNESSES,
      :old.OTHER_INVOLVED,
      :old.NATURE,
      :old.LOCATION,
      :old.CLIENT_ISSUES,
      :old.DESCRIPTION,
      :old.DISPOSITION,
      :old.RESTRICTION,
      :old.CHARGES_LAID,
      :old.POLICE_REPORT_NO,
      :old.BADGE_NO,
      :old.INVESTIGATION_RCMD,
      :old.INVESTIGATION_CONDUCTEDBY,
      :old.INVESTIGATION_DATE,
      :old.FOLLOWUP_INFO,
      :old.FOLLOWUP_COMPLETEDBY ,
      :old.FOLLOWUP_DATE,
      :old.REPORT_COMPLETED,
      :old.PROGRAM_ID,
      QUATROSHELTER_LOG.SEQ_INCIDENT_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_INCIDENT;
/

prompt
prompt Creating trigger TRI_INCIDENT_CLIENT_STAFF
prompt ==========================================
prompt
create or replace trigger "TRI_INCIDENT_CLIENT_STAFF"
after insert or update of clients,staff or delete ON QUATROSHELTER.INCIDENT
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
  v_demographId VARCHAR2(10) := '';
  v_client      varchar2(1000) := :new.clients;
  v_staff       varchar2(1000) := :new.staff;
  v_staffId     varchar2(6) := '';
BEGIN
  BEGIN
    DELETE FROM INCIDENT_CLIENT A WHERE A.INCIDENTID = :new.ID;
    
    if (not v_client is null) then
      v_client := substr(v_client, 0, instr(v_client, '/') - 1);     
      if (instr(v_client, ':') > 0) then      
          v_demographId := substr(v_client, 0, instr(v_client, ':') - 1);
          INSERT INTO QUATROSHELTER.INCIDENT_CLIENT
          VALUES
            (:new.ID, to_number(v_demographId));
          v_client := substr(v_client, instr(v_client, ':') + 1);
          if (instr(v_client, ':') = 0) then  
             INSERT INTO QUATROSHELTER.INCIDENT_CLIENT
             VALUES(:new.ID, to_number(v_client));  
          end if;                
      else
        INSERT INTO QUATROSHELTER.INCIDENT_CLIENT
        VALUES(:new.ID, to_number(substr(:new.clients, 0, instr(:new.clients, '/') - 1)));        
      end if;      
    end if;
    
    DELETE FROM INCIDENT_STAFF A WHERE A.INCIDENTID = :new.ID;
   
    if (not v_staff is null) then
      v_staff := substr(v_staff, 0, instr(v_staff, '/') - 1);
      if (instr(v_staff, ':') > 0) then
       
          v_staffId := substr(v_staff, 0, instr(v_staff, ':') - 1);
         -- dbms_output.put_line(v_staff);
        --  dbms_output.put_line(v_staffId);
          INSERT INTO QUATROSHELTER.INCIDENT_STAFF
          VALUES
            (:new.ID, v_staffId);
          v_staff := substr(v_staff, instr(v_staff, ':') + 1);
          if (instr(v_staff, ':') = 0) then
            INSERT INTO QUATROSHELTER.INCIDENT_STAFF
            VALUES(:new.ID, v_staff);
          end if;
        
      else
        INSERT INTO QUATROSHELTER.INCIDENT_STAFF VALUES (:new.ID, v_staff);
      end if;
    end if;
  EXCEPTION
    WHEN OTHERS THEN
      NULL;
  END;
END TRI_INCIDENT_CLIENT_STAFF;
/

prompt
prompt Creating trigger TRI_INTAKE
prompt ===========================
prompt
create or replace trigger "TRI_INTAKE"
after update or delete ON INTAKE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.INTAKE_LOG VALUES
    (
    :old.INTAKE_ID,
    :old.INTAKE_NODE_ID,
    :old.CLIENT_ID,
    :old.STAFF_ID,
    :old.CREATION_DATE,
    :old.INTAKE_STATUS,
    :old.INTAKE_LOCATION,
    :old.PROGRAM_ID,
    :old.LASTUPDATEDATE,
    quatroshelter_log.seq_intake_log.NEXTVAL,
    v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_INTAKE;
/

prompt
prompt Creating trigger TRI_INTAKE_ANSWER
prompt ==================================
prompt
create or replace trigger "TRI_INTAKE_ANSWER"
after update or delete ON INTAKE_ANSWER
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.INTAKE_ANSWER_LOG VALUES
    (
     :old.INTAKE_ANSWER_ID,
    :old.INTAKE_ID,
    :old.INTAKE_NODE_ID,
    :old.VAL,
    quatroshelter_log.seq_intake_answer_log.nextval,
    v_action,
    :old.lastupdateuser,
    :old.lastupdatedate
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_INTAKE_ANSWER;
/

prompt
prompt Creating trigger TRI_INTAKE_FAMILY
prompt ==================================
prompt
create or replace trigger "TRI_INTAKE_FAMILY"
after update or delete ON INTAKE_FAMILY
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.INTAKE_FAMILY_LOG VALUES
    (
    :old.INTAKE_HEAD_ID,
    :old.INTAKE_ID,
    :old.MEMBER_STATUS,
    :old.RELATIONSHIP,
    :old.JOIN_FAMILY_DATE,
    :old.LEAVE_FAMILY_DATE,
    :old.LASTUPDATEUSER,
    :old.LASTUPDATEDATE,
    quatroshelter_log.seq_intake_family_log.NEXTVAL,
    v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_INTAKE_FAMILY;
/

prompt
prompt Creating trigger TRI_INTAKE_FAMILY_HIS
prompt ======================================
prompt
create or replace trigger "TRI_INTAKE_FAMILY_HIS"
before insert or update or delete ON INTAKE_FAMILY
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF DELETING THEN
       UPDATE INTAKE_FAMILY_HISTORY
       SET LEAVE_FAMILY_DATE = sysdate,
           LASTUPDATEDATE = sysdate,
           LASTUPDATEUSER = :old.lastupdateuser
       WHERE INTAKE_HEAD_ID = :old.intake_head_id
       and   INTAKE_ID = :old.intake_id
       and JOIN_FAMILY_DATE = :old.JOIN_FAMILY_DATE
       ;
    ELSIF UPDATING THEN
      UPDATE INTAKE_FAMILY_HISTORY
      SET
        MEMBER_STATUS = :new.MEMBER_STATUS,
        RELATIONSHIP = :new.RELATIONSHIP,
        LEAVE_FAMILY_DATE = :new.LEAVE_FAMILY_DATE,
        LASTUPDATEUSER = :new.LASTUPDATEUSER,
        LASTUPDATEDATE = :new.LASTUPDATEDATE
       WHERE
        INTAKE_HEAD_ID = :new.INTAKE_HEAD_ID
        And INTAKE_ID = :new.INTAKE_ID
        and JOIN_FAMILY_DATE = :new.JOIN_FAMILY_DATE
        ;
    ELSE    
      INSERT INTO INTAKE_FAMILY_HISTORY VALUES
      (
        :new.INTAKE_HEAD_ID,
        :new.INTAKE_ID,
        :new.MEMBER_STATUS,
        :new.RELATIONSHIP,
        :new.JOIN_FAMILY_DATE,
        :new.LEAVE_FAMILY_DATE,
        :new.LASTUPDATEUSER,
        :new.LASTUPDATEDATE
       )
       ;
    END IF;
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_INTAKE;
/

prompt
prompt Creating trigger TRI_LOG
prompt ========================
prompt
create or replace trigger "TRI_LOG"
after UPDATE or delete ON LOG
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LOG_LOG  VALUES
      (
	    :old.DATETIME,
      :old.PROVIDER_NO,
      :old.ACTION,
      :old.CONTENT,
      :old.CONTENTID,
      :old.IP,
      QUATROSHELTER_LOG.SEQ_LOG_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LOG;
/

prompt
prompt Creating trigger TRI_LST_ABORIGINAL
prompt ===================================
prompt
create or replace trigger "TRI_LST_ABORIGINAL"
after UPDATE or delete ON LST_ABORIGINAL
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_ABORIGINAL_LOG  VALUES
      (
	    :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_ABORIGINAL_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_ABORIGINAL;
/

prompt
prompt Creating trigger TRI_LST_ADMISSION_STATUS
prompt =========================================
prompt
create or replace trigger "TRI_LST_ADMISSION_STATUS"
after UPDATE or delete ON LST_ADMISSION_STATUS
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_ADMISSION_STATUS_LOG  VALUES
      (
	    :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_ADMISSION_STATUS_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_ADMISSION_STATUS;
/

prompt
prompt Creating trigger TRI_LST_BED_TYPE
prompt =================================
prompt
create or replace trigger "TRI_LST_BED_TYPE"
after UPDATE or delete ON LST_BED_TYPE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_BED_TYPE_LOG  VALUES
      (
	  :old.BED_TYPE_ID,
    :old.NAME,
    :old.ISACTIVE,
    :old.LASTUPDATEDATE,
	  :old.LASTUPDATEUSER,
      QUATROSHELTER_LOG.SEQ_LST_BED_TYPE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_BED_TYPE;
/

prompt
prompt Creating trigger TRI_LST_CASESTATUS
prompt ===================================
prompt
create or replace trigger "TRI_LST_CASESTATUS"
after UPDATE or delete ON LST_CASESTATUS
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_CASESTATUS_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_CASESTATUS_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_CASESTATUS;
/

prompt
prompt Creating trigger TRI_LST_COMPLAINT_METHOD
prompt =========================================
prompt
create or replace trigger "TRI_LST_COMPLAINT_METHOD"
after UPDATE or delete ON LST_COMPLAINT_METHOD
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_COMPLAINT_METHOD_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_COMPLAINT_METHOD_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_COMPLAINT_METHOD;
/

prompt
prompt Creating trigger TRI_LST_COMPLAINT_OUTCOME
prompt ==========================================
prompt
create or replace trigger "TRI_LST_COMPLAINT_OUTCOME"
after UPDATE or delete ON LST_COMPLAINT_OUTCOME
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_COMPLAINT_OUTCOME_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_COMPLAINT_OUTCOME_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_COMPLAINT_OUTCOME;
/

prompt
prompt Creating trigger TRI_LST_COMPLAINT_SECTION
prompt ==========================================
prompt
create or replace trigger "TRI_LST_COMPLAINT_SECTION"
after UPDATE or delete ON LST_COMPLAINT_SECTION
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_COMPLAINT_SECTION_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_COMPLAINT_SECTION_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_COMPLAINT_SECTION;
/

prompt
prompt Creating trigger TRI_LST_COMPLAINT_SOURCE
prompt =========================================
prompt
create or replace trigger "TRI_LST_COMPLAINT_SOURCE"
after UPDATE or delete ON LST_COMPLAINT_SOURCE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_COMPLAINT_SOURCE_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_COMPLAINT_SOURCE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_COMPLAINT_SOURCE;
/

prompt
prompt Creating trigger TRI_LST_COMPLAINT_SUBSECTION
prompt =============================================
prompt
create or replace trigger "TRI_LST_COMPLAINT_SUBSECTION"
after UPDATE or delete ON LST_COMPLAINT_SUBSECTION
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_COMPLAINT_SUBSECTION_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :OLD.SECTIONID,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_COMPLAINT_SUBSEC_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_COMPLAINT_SUBSECTION;
/

prompt
prompt Creating trigger TRI_LST_COMPONENTOFSERVICE
prompt ===========================================
prompt
create or replace trigger "TRI_LST_COMPONENTOFSERVICE"
after UPDATE or delete ON LST_COMPONENTOFSERVICE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_COMPONENTOFSERVICE_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_COMPONENTOFSERVICE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_COMPONENTOFSERVICE;
/

prompt
prompt Creating trigger TRI_LST_COUNTRY
prompt ================================
prompt
create or replace trigger "TRI_LST_COUNTRY"
after UPDATE or delete ON LST_COUNTRY
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_COUNTRY_LOG  VALUES
      (
	    :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_COUNTRY_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_COUNTRY;
/

prompt
prompt Creating trigger TRI_LST_CURSLEEPARRANGEMENT
prompt ============================================
prompt
create or replace trigger "TRI_LST_CURSLEEPARRANGEMENT"
after UPDATE or delete ON LST_CURSLEEPARRANGEMENT
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_CURSLEEPARRANGEMENT_LOG  VALUES
      (
	    :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_CURSLEEPARRANGE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_CURSLEEPARRANGEMENT;
/

prompt
prompt Creating trigger TRI_LST_DISCHARGE_REASON
prompt =========================================
prompt
create or replace trigger "TRI_LST_DISCHARGE_REASON"
after UPDATE or delete ON LST_DISCHARGE_REASON
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_DISCHARGE_REASON_LOG  VALUES
      (
      :old.DESCRIPTION,
      :OLD.NEEDSECONDARY,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.CODE,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_DISCHARGE_REASON_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_DISCHARGE_REASON;
/

prompt
prompt Creating trigger TRI_LST_DOCUMENTCATEGORY
prompt =========================================
prompt
create or replace trigger "TRI_LST_DOCUMENTCATEGORY"
after UPDATE or delete ON LST_DOCUMENTCATEGORY
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_DOCUMENTCATEGORY_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_DOCUMENTCATEGORY_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_DOCUMENTCATEGORY;
/

prompt
prompt Creating trigger TRI_LST_DOCUMENTTYPE
prompt =====================================
prompt
create or replace trigger "TRI_LST_DOCUMENTTYPE"
after UPDATE or delete ON LST_DOCUMENTTYPE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_DOCUMENTTYPE_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :OLD.MIME,
      :OLD.SHORTDESC,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_DOCUMENTTYPE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_DOCUMENTTYPE;
/

prompt
prompt Creating trigger TRI_LST_FIELDTYPE
prompt ==================================
prompt
create or replace trigger "TRI_LST_FIELDTYPE"
after UPDATE or delete ON LST_FIELDTYPE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_FIELDTYPE_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ACTIVEYN,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_FIELDTYPE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_FIELDTYPE;
/

prompt
prompt Creating trigger TRI_LST_FIELD_CATEGORY
prompt =======================================
prompt
create or replace trigger "TRI_LST_FIELD_CATEGORY"
after UPDATE or delete ON LST_FIELD_CATEGORY
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_FIELD_CATEGORY_LOG  VALUES
      (
      :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_FIELD_CATEGORY_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_FIELD_CATEGORY;
/

prompt
prompt Creating trigger TRI_LST_GENDER
prompt ===============================
prompt
create or replace trigger "TRI_LST_GENDER"
after UPDATE or delete ON LST_GENDER
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_GENDER_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :OLD.PROGDESC,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_GENDER_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_GENDER;
/

prompt
prompt Creating trigger TRI_LST_INCIDENT_CLIENTISSUES
prompt ==============================================
prompt
create or replace trigger "TRI_LST_INCIDENT_CLIENTISSUES"
after UPDATE or delete ON LST_INCIDENT_CLIENTISSUES
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_INCIDENT_CLIENTISSUES_LOG  VALUES
      (
      :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_INC_CLIENTISSUES_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_INCIDENT_CLIENTISSUES;
/

prompt
prompt Creating trigger TRI_LST_INCIDENT_DISPOSITION
prompt =============================================
prompt
create or replace trigger "TRI_LST_INCIDENT_DISPOSITION"
after UPDATE or delete ON LST_INCIDENT_DISPOSITION
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_INCIDENT_DISPOSITION_LOG  VALUES
      (
      :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_INC_DISPOSITION_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_INCIDENT_DISPOSITION;
/

prompt
prompt Creating trigger TRI_LST_INCIDENT_NATURE
prompt ========================================
prompt
create or replace trigger "TRI_LST_INCIDENT_NATURE"
after UPDATE or delete ON LST_INCIDENT_NATURE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_INCIDENT_NATURE_LOG  VALUES
      (
      :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_INCIDENT_NATURE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_INCIDENT_NATURE;
/

prompt
prompt Creating trigger TRI_LST_INCIDENT_OTHERS
prompt ========================================
prompt
create or replace trigger "TRI_LST_INCIDENT_OTHERS"
after UPDATE or delete ON LST_INCIDENT_OTHERS
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_INCIDENT_OTHERS_LOG  VALUES
      (
      :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_INCIDENT_OTHERS_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_INCIDENT_OTHERS;
/

prompt
prompt Creating trigger TRI_LST_INTAKE_REJECT_REASON
prompt =============================================
prompt
create or replace trigger "TRI_LST_INTAKE_REJECT_REASON"
after UPDATE or delete ON LST_INTAKE_REJECT_REASON
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_INTAKE_REJECT_REASON_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_INTAKEREJECTREASON_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_INTAKE_REJECT_REASON;
/

prompt
prompt Creating trigger TRI_LST_LANGUAGE
prompt =================================
prompt
create or replace trigger "TRI_LST_LANGUAGE"
after UPDATE or delete ON LST_LANGUAGE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_LANGUAGE_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_LANGUAGE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_LANGUAGE;
/

prompt
prompt Creating trigger TRI_LST_LENGTHOFHOMELESS
prompt =========================================
prompt
create or replace trigger "TRI_LST_LENGTHOFHOMELESS"
after UPDATE or delete ON LST_LENGTHOFHOMELESS
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_LENGTHOFHOMELESS_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_LENGTHOFHOMELESS_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_LENGTHOFHOMELESS;
/

prompt
prompt Creating trigger TRI_LST_LIVEDBEFORE
prompt ====================================
prompt
CREATE OR REPLACE TRIGGER "TRI_LST_LIVEDBEFORE"
after UPDATE or delete ON LST_LIVEDBEFORE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_LIVEDBEFORE_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_LIVEDBEFORE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_LIVEDBEFORE;
/

prompt
prompt Creating trigger TRI_LST_MESSAGE_TYPE
prompt =====================================
prompt
create or replace trigger "TRI_LST_MESSAGE_TYPE"
after UPDATE or delete ON LST_MESSAGE_TYPE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_MESSAGE_TYPE_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ACTIVEYN,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_MESSAGE_TYPE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_MESSAGE_TYPE;
/

prompt
prompt Creating trigger TRI_LST_OPERATOR
prompt =================================
prompt
create or replace trigger "TRI_LST_OPERATOR"
after UPDATE or delete ON LST_OPERATOR
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_OPERATOR_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ACTIVEYN,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_OPERATOR_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_OPERATOR;
/

prompt
prompt Creating trigger TRI_LST_ORGANIZATION
prompt =====================================
prompt
create or replace trigger "TRI_LST_ORGANIZATION"
after UPDATE or delete ON LST_ORGANIZATION
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_ORGANIZATION_LOG  VALUES
      (
      :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_ORGANIZATION_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_ORGANIZATION;
/

prompt
prompt Creating trigger TRI_LST_ORGANIZATION_DEACTIVE
prompt ==============================================
prompt
create or replace trigger "TRI_LST_ORGANIZATION_DEACTIVE"
after update of ISACTIVE ON LST_ORGANIZATION
REFERENCING NEW AS NEW OLD AS OLD
for each row
BEGIN
  BEGIN
    IF :new.ISACTIVE = 0 THEN
      UPDATE LST_SHELTER
      SET ACTIVE=0
      WHERE ORGID = :new.ID
      ;
    END IF;
 EXCEPTION
    WHEN OTHERS THEN NULL;
 END;
END "TRI_LST_ORGANIZATION_DEACTIVE";
/

prompt
prompt Creating trigger TRI_LST_ORGCD
prompt ==============================
prompt
create or replace trigger "TRI_LST_ORGCD"
after UPDATE or delete ON LST_ORGCD
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_ORGCD_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ACTIVEYN,
      :old.ORDERBYINDEX,
      :OLD.CODETREE,
      :OLD.FULLCODE,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_ORGCD_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_ORGCD;
/

prompt
prompt Creating trigger TRI_LST_PROGRAM_TYPE
prompt =====================================
prompt
create or replace trigger "TRI_LST_PROGRAM_TYPE"
after UPDATE or delete ON LST_PROGRAM_TYPE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_PROGRAM_TYPE_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_PROGRAM_TYPE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_PROGRAM_TYPE;
/

prompt
prompt Creating trigger TRI_LST_PROVINCE
prompt =================================
prompt
create or replace trigger "TRI_LST_PROVINCE"
after UPDATE or delete ON LST_PROVINCE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_PROVINCE_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_PROVINCE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_PROVINCE;
/

prompt
prompt Creating trigger TRI_LST_REASONFORHOMELESS
prompt ==========================================
prompt
create or replace trigger "TRI_LST_REASONFORHOMELESS"
after UPDATE or delete ON LST_REASONFORHOMELESS
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_REASONFORHOMELESS_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_REASONFORHOMELESS_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_REASONFORHOMELESS;
/

prompt
prompt Creating trigger TRI_LST_REASONFORSERVICE
prompt =========================================
prompt
create or replace trigger "TRI_LST_REASONFORSERVICE"
after UPDATE or delete ON LST_REASONFORSERVICE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_REASONFORSERVICE_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_REASONFORSERVICE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_REASONFORSERVICE;
/

prompt
prompt Creating trigger TRI_LST_REASONNOADMIT
prompt ======================================
prompt
create or replace trigger "TRI_LST_REASONNOADMIT"
after UPDATE or delete ON LST_REASONNOADMIT
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_REASONNOADMIT_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_REASONNOADMIT_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_REASONNOADMIT;
/

prompt
prompt Creating trigger TRI_LST_REASON_NOTSIGN
prompt =======================================
prompt
create or replace trigger "TRI_LST_REASON_NOTSIGN"
after UPDATE or delete ON LST_REASON_NOTSIGN
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_REASON_NOTSIGN_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_REASON_NOTSIGN_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_REASON_NOTSIGN;
/

prompt
prompt Creating trigger TRI_LST_REFERREDBY
prompt ===================================
prompt
create or replace trigger "TRI_LST_REFERREDBY"
after UPDATE or delete ON LST_REFERREDBY
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_REFERREDBY_LOG  VALUES
      (
      :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_REFERREDBY_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_REFERREDBY;
/

prompt
prompt Creating trigger TRI_LST_REFERREDTO
prompt ===================================
prompt
create or replace trigger "TRI_LST_REFERREDTO"
after UPDATE or delete ON LST_REFERREDTO
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_REFERREDTO_LOG  VALUES
      (
	    :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_REFERREDBY_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_REFERREDTO;
/

prompt
prompt Creating trigger TRI_LST_ROOM_TYPE
prompt ==================================
prompt
create or replace trigger "TRI_LST_ROOM_TYPE"
after update or delete ON LST_ROOM_TYPE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.LST_ROOM_TYPE_LOG VALUES
    (
      :old.ROOM_TYPE_ID,
      :old.NAME,
      :old.DFLT,
      :old.DISPLAYORDER,
      :old.ISACTIVE,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_LST_ROOM_TYPE_log.NEXTVAL,
      v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_ROOM_TYPE;
/

prompt
prompt Creating trigger TRI_LST_SECTOR
prompt ===============================
prompt
create or replace trigger "TRI_LST_SECTOR"
after UPDATE or delete ON LST_SECTOR
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_SECTOR_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_SECTOR_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_SECTOR;
/

prompt
prompt Creating trigger TRI_LST_SERVICE_RESTRICTION
prompt ============================================
prompt
create or replace trigger "TRI_LST_SERVICE_RESTRICTION"
after UPDATE or delete ON LST_SERVICE_RESTRICTION
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_SERVICE_RESTRICTION_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_SERVICE_RESTRICT_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_SERVICE_RESTRICTION;
/

prompt
prompt Creating trigger TRI_LST_SHELTER
prompt ================================
prompt
create or replace trigger "TRI_LST_SHELTER"
after UPDATE or delete ON LST_SHELTER
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_SHELTER_LOG  VALUES
      (
	    :old.ID,
      :old.NAME,
      :old.DESCRIPTION,
      :old.CONTACT_NAME,
      :old.ACTIVE,
      :old.ORGID,
      :old.TYPE,
      :old.STREET_1,
      :old.STREET_2,
      :old.CITY ,
      :old.PROVINCE,
      :old.POSTAL_CODE,
      :old.TELEPHONE,
      :old.FAX,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_SHELTER_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_SHELTER;
/

prompt
prompt Creating trigger TRI_LST_SHELTER_DEACTIVE
prompt =========================================
prompt
create or replace trigger "TRI_LST_SHELTER_DEACTIVE"
after update OF ACTIVE ON LST_SHELTER
REFERENCING NEW AS NEW OLD AS OLD
for each row
BEGIN
  BEGIN
    IF :new.ACTIVE = 0 THEN
      UPDATE FACILITY
      SET ACTIVE=0
      WHERE ORG_ID = :new.ID
      ;
    END IF;
 EXCEPTION
    WHEN OTHERS THEN NULL;
 END;
END "TRI_LST_SHELTER_DEACTIVE";
/

prompt
prompt Creating trigger TRI_LST_SHELTER_STANDARDS
prompt ==========================================
prompt
create or replace trigger "TRI_LST_SHELTER_STANDARDS"
after UPDATE or delete ON LST_SHELTER_STANDARDS
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_SHELTER_STANDARDS_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.SECTIONID,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_SHELTER_STANDARDS_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_SHELTER_STANDARDS;
/

prompt
prompt Creating trigger TRI_LST_SOURCEINCOME
prompt =====================================
prompt
create or replace trigger "TRI_LST_SOURCEINCOME"
after UPDATE or delete ON LST_SOURCEINCOME
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_SOURCEINCOME_LOG  VALUES
      (
	    :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_SOURCEINCOME_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_SOURCEINCOME;
/

prompt
prompt Creating trigger TRI_LST_STATUSINCANADA
prompt =======================================
prompt
create or replace trigger "TRI_LST_STATUSINCANADA"
after UPDATE or delete ON LST_STATUSINCANADA
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_STATUSINCANADA_LOG  VALUES
      (
	    :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_STATUSINCANADA_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_STATUSINCANADA;
/

prompt
prompt Creating trigger TRI_LST_TITLE
prompt ==============================
prompt
create or replace trigger "TRI_LST_TITLE"
after UPDATE or delete ON LST_TITLE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_TITLE_LOG  VALUES
      (
	    :old.ID,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_TITLE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_TITLE;
/

prompt
prompt Creating trigger TRI_LST_TRANSPORTATION_TYPE
prompt ============================================
prompt
create or replace trigger "TRI_LST_TRANSPORTATION_TYPE"
after UPDATE or delete ON LST_TRANSPORTATION_TYPE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.LST_TRANSPORTATION_TYPE_LOG  VALUES
      (
	    :old.CODE,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      QUATROSHELTER_LOG.SEQ_LST_TRANSPORT_TYPE_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_LST_TRANSPORTATION_TYPE;
/

prompt
prompt Creating trigger TRI_PMM_LOG
prompt ============================
prompt
create or replace trigger "TRI_PMM_LOG"
after UPDATE or delete ON PMM_LOG
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.PMM_LOG  VALUES
      (
	    :old.ID,
      :old.PROVIDER_NO,
      :old.DATETIME,
      :old.ACTION,
      :old.CONTENTID,
      :OLD.CONTENT,
      :OLD.IP,
      QUATROSHELTER_LOG.SEQ_PMM_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_PMM_LOG;
/

prompt
prompt Creating trigger TRI_PROGRAM
prompt ============================
prompt
create or replace trigger "TRI_PROGRAM"
after update or delete ON PROGRAM
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.PROGRAM_LOG VALUES
    (
      :old.PROGRAM_ID,
      :old.USERDEFINED,
      :old.SHELTER_ID,
      :old.INTAKE_PROGRAM,
      :old.NAME,
      :old.DESCR,
      :old.ADDRESS,
      :old.PHONE,
      :old.FAX,
      :old.URL,
      :old.EMAIL,
      :old.EMERGENCY_NUMBER,
      :old.TYPE,
      :old.LOCATION,
      :old.MAX_ALLOWED,
      :old.NUM_OF_MEMBERS,
      :old.HOLDING_TANK,
      :old.ALLOW_BATCH_ADMISSION,
      :old.ALLOW_BATCH_DISCHARGE,
      :old.HIC,
      :old.PROGRAM_STATUS ,
      :old.BED_PROGRAM_LINK_ID,
      :old.MANORWOMAN,
      :old.TRANSGENDER,
      :old.FIRSTNATION ,
      :old.BEDPROGRAMAFFILIATED,
      :old.ALCOHOL  ,
      :old.ABSTINENCESUPPORT,
      :old.PHYSICALHEALTH,
      :old.MENTALHEALTH,
      :old.HOUSING ,
      :old.EXCLUSIVE_VIEW ,
      :old.MAXIMUM_RESTRICTION_DAYS,
      :old.DEFAULT_RESTRICTION_DAYS,
      :old.AGEMIN,
      :old.AGEMAX,
      :old.FACILITY_ID,
      :old.CAPACITY_FUNDING,
      :old.CAPACITY_SPACE,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_program_log.NEXTVAL,
      v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_PROGRAM;
/

prompt
prompt Creating trigger TRI_PROGRAM_CLIENT_RESTRICTION
prompt ===============================================
prompt
create or replace trigger "TRI_PROGRAM_CLIENT_RESTRICTION"
after update or delete ON PROGRAM_CLIENT_RESTRICTION
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.PROGRAM_CLIENT_RESTRICTION_LOG VALUES
    (
      :old.ID,
      :old.PROGRAM_ID,
      :old.DEMOGRAPHIC_NO,
      :old.PROVIDER_NO,
      :old.COMMENTS,
      :old.IS_ENABLED,
      :old.START_DATE,
      :old.END_DATE,
      :old.EARLY_TERMINATION_PROVIDER,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_programclientrestrict_log.NEXTVAL,
      v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_PROGRAM_CLIENT_RESTRICTION;
/

prompt
prompt Creating trigger TRI_PROGRAM_QUEUE
prompt ==================================
prompt
create or replace trigger "TRI_PROGRAM_QUEUE"
after update or delete ON PROGRAM_QUEUE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.PROGRAM_QUEUE_LOG VALUES
    (
      :old.queue_id,
      :old.client_id,
      :old.referral_date,
      :old.provider_no,
      :old.notes,
      :old.program_id,
      :old.status,
      :old.referral_id,
      :old.present_problems,
      quatroshelter_log.seq_program_queue_log.NEXTVAL,
      v_action,
      :old.intake_id
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_PROGRAM_QUEUE;
/

prompt
prompt Creating trigger TRI_PROGRAM_SHELTER_ID
prompt =======================================
prompt
CREATE OR REPLACE TRIGGER TRI_PROGRAM_SHELTER_ID
BEFORE INSERT OR UPDATE OF FACILITY_ID
ON PROGRAM
FOR EACH ROW
DECLARE
       IDX NUMBER;
BEGIN
     SELECT ORG_ID INTO IDX FROM FACILITY
     WHERE ID = :new.FACILITY_ID;
     :new.SHELTER_ID := IDX;
EXCEPTION
     WHEN NO_DATA_FOUND THEN
          NULL;
END;
/

prompt
prompt Creating trigger TRI_PROVIDER
prompt =============================
prompt
create or replace trigger "TRI_PROVIDER"
after update or delete ON PROVIDER
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.PROVIDER_LOG VALUES
    (
      :old.PROVIDER_NO,
      :old.LAST_NAME,
      :old.FIRST_NAME,
      :old.PROVIDER_TYPE,
      :old.SPECIALTY ,
      :old.TEAM ,
      :old.SEX ,
      :old.DOB,
      :old.ADDRESS ,
      :old.PHONE ,
      :old.WORK_PHONE,
      :old.OHIP_NO ,
      :old.RMA_NO ,
      :old.BILLING_NO ,
      :old.HSO_NO,
      :old.STATUS,
      :old.COMMENTS,
      :old.PROVIDER_ACTIVITY ,
      :old.INIT  ,
      :old.JOB_TITLE ,
      :old.EMAIL ,
      :old.TITLE  ,
      :old.LASTUPDATEUSER ,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_PROVIDER_log.NEXTVAL,
      v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_PROVIDER;
/

prompt
prompt Creating trigger TRI_REPORT
prompt ===========================
prompt
create or replace trigger "TRI_REPORT"
after update or delete ON REPORT
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.REPORT_LOG VALUES
    (
      :old.REPORTNO,
      :old.TITLE,
      :old.DESCRIPTION,
      :old.ORGAPPLICABLE,
      :old.REPORTTYPE,
      :old.DATEOPTION,
      :old.DATEPART,
      :old.REPORTGROUP,
      :old.NOTES,
      :old.TABLENAME,
      :old.UPDATEDBY,
      :old.UPDATEDDATE,
      :old.SPTORUN,
      quatroshelter_log.seq_REPORT_log.NEXTVAL,
      v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_REPORT;
/

prompt
prompt Creating trigger TRI_REPORT_LK_REPORTGROUP
prompt ==========================================
prompt
create or replace trigger "TRI_REPORT_LK_REPORTGROUP"
after update or delete ON REPORT_LK_REPORTGROUP
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.REPORT_LK_REPORTGROUP_LOG VALUES
    (
      :old.ID,
      :old.DESCRIPTION,
      :old.ACTIVEYN,
      :old.ORDERBYINDEX ,
      :old.LASTUPDATEUSER ,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_REPORT_LK_REPORTGROUP_log.NEXTVAL,
      v_action,
      :OLD.NOTE
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_REPORT_LK_REPORTGROUP;
/

prompt
prompt Creating trigger TRI_ROOM
prompt =========================
prompt
create or replace trigger "TRI_ROOM"
after update or delete ON ROOM
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.ROOM_LOG VALUES
    (
      :old.ROOM_ID,
      :old.ROOM_TYPE_ID,
      :old.PROGRAM_ID,
      :old.NAME,
      :old.FLOOR,
      :old.ACTIVE,
      :old.FACILITY_ID,
      :old.ASSIGNED_BED,
      :old.OCCUPANCY,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_ROOM_log.NEXTVAL,
      v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_ROOM;
/

prompt
prompt Creating trigger TRI_ROOM_DEMOGRAPHIC
prompt =====================================
prompt
create or replace trigger "TRI_ROOM_DEMOGRAPHIC"
after update or delete ON ROOM_DEMOGRAPHIC
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.ROOM_DEMOGRAPHIC_LOG VALUES
    (
      :old.ROOM_ID,
      :old.DEMOGRAPHIC_NO,
      :old.PROVIDER_NO,
      :old.ASSIGN_START,
      :old.ASSIGN_END,
      :old.COMMENTS,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_ROOM_DEMOGRAPHIC_log.NEXTVAL,
      v_action,
      :old.BED_ID
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_ROOM_DEMOGRAPHIC;
/

prompt
prompt Creating trigger TRI_ROOM_DEMOGRAPHIC_HIS
prompt =========================================
prompt
create or replace trigger "TRI_ROOM_DEMOGRAPHIC_HIS"
after update or delete ON ROOM_DEMOGRAPHIC_HISTORICAL
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.ROOM_DEMOGRAPHIC_HIS_LOG VALUES
    (
      :old.ROOM_ID,
      :old.ADMISSION_ID,
      :old.DEMOGRAPHIC_NO,
      :old.USAGE_START,
      :old.USAGE_END,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_ROOM_DEMOGRAPHIC_HIS_log.NEXTVAL,
      v_action,
      :old.BED_ID,
      :old.RECORDID
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_ROOM_DEMOGRAPHIC_HIS;
/

prompt
prompt Creating trigger TRI_ROOM_TYPE
prompt ==============================
prompt
create or replace trigger "TRI_ROOM_TYPE"
after update or delete ON LST_ROOM_TYPE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.LST_ROOM_TYPE_LOG VALUES
    (
      :old.ROOM_TYPE_ID,
      :old.NAME,
      :old.DFLT,
      :old.DISPLAYORDER,
      :old.ISACTIVE,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_LST_ROOM_TYPE_log.NEXTVAL,
      v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_ROOM_TYPE;
/

prompt
prompt Creating trigger TRI_SECROLE
prompt ============================
prompt
create or replace trigger "TRI_SECROLE"
after update or delete ON SECROLE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.SECROLE_LOG VALUES
    (
      :old.ROLE_NAME,
      :old.DESCRIPTION,
      :old.ISACTIVE,
      :old.DISPLAYORDER,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_SECROLE_log.NEXTVAL,
      v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_SECROLE;
/

prompt
prompt Creating trigger TRI_SECSITE
prompt ============================
prompt
create or replace trigger "TRI_SECSITE"
after update or delete ON SECSITE
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.SECSITE_LOG VALUES
    (
      :old.SITEID,
      :old.DESCRIPTION,
      :old.ACTIVE,
      :old.SITEKEY,
      :old.CREATEDBY,
      :old.CREATEDDATE,
      :old.LASTACCESSED,
      quatroshelter_log.seq_SECSITE_log.NEXTVAL,
      v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_SECSITE;
/

prompt
prompt Creating trigger TRI_SECURITY
prompt =============================
prompt
create or replace trigger "TRI_SECURITY"
after update or delete ON SECURITY
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
   IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;

    INSERT INTO QUATROSHELTER_LOG.SECURITY_LOG VALUES
    (
      :old.SECURITY_NO,
      :old.USER_NAME,
      :old.PASSWORD,
      :old.PROVIDER_NO,
      :old.PIN,
      :old.B_REMOTELOCKSET,
      :old.B_LOCALLOCKSET,
      :old.DATE_EXPIREDATE,
      :old.B_EXPIRESET,
      :old.LASTUPDATEUSER,
      :old.LASTUPDATEDATE,
      quatroshelter_log.seq_SECURITY_log.NEXTVAL,
      v_action
    );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_SECURITY;
/

prompt
prompt Creating trigger TRI_SECURITY_ID
prompt ================================
prompt
CREATE OR REPLACE TRIGGER TRI_SECURITY_ID
BEFORE INSERT
ON SECURITY
FOR EACH ROW
DECLARE
       IDX NUMBER;
BEGIN

     SELECT HIBERNATE_SEQUENCE.NEXTVAL INTO IDX FROM DUAL;
     :new.security_no := IDX;
END;
/

prompt
prompt Creating trigger TRI_UPDATEREPORTINTAKE
prompt =======================================
prompt
CREATE OR REPLACE TRIGGER tri_updateReportIntake
AFTER INSERT OR UpDATE
ON INTAKE_ANSWER
FOR EACH ROW
DECLARE
          v_intake_id                     intake.intake_id%type;
          v_intake_node_id                intake_answer.intake_node_id%type;
          v_value                         varchar2(4000);
          v_sql                           varchar2(4000);
          v_count                         int:=0;
          v_insertYN                      varchar2(1):='N';
          v_column                        varchar2(32);
BEGIN
        v_intake_id := :new.intake_id;
        v_intake_node_id := :new.intake_node_id;
        v_value := replace(:new.val,'''','''''');

        select count(*) into v_count
        from report_intake where intake_id =v_intake_id;
        if v_count=0 then v_insertYN:='Y'; end if;
        if v_intake_node_id=1 then
               v_column := 'REFERREDBY';
            elsif  v_intake_node_id=2 then
               v_column :='ABORIGINAL';
            elsif  v_intake_node_id=3 then
               v_column :='CURSLEEPARRANGEMENT';
            elsif  v_intake_node_id=4 then
               v_column :='LENGTHOFHOMELESS';
            elsif  v_intake_node_id=5 then
               v_column :='REASONFORHOMELESS';
            elsif  v_intake_node_id=6 then
               v_column :='SOURCEINCOME';
            elsif  v_intake_node_id=7 then
               v_column :='LIVEDBEFORE';
            elsif  v_intake_node_id=8 then
               v_column :='STATUSINCANADA';
            elsif  v_intake_node_id=9 then
               v_column :='REFERREDTO';
            elsif  v_intake_node_id=10 then
               v_column :='REASONNOADMIT';
            elsif  v_intake_node_id=11 then
               v_column :='CONTACTNAME';
            elsif  v_intake_node_id=12 then
               v_column :='CONTACTNUMBER';
            elsif  v_intake_node_id=13 then
               v_column :='CONTACTEMAIL';
            elsif  v_intake_node_id=14 then
               v_column :='YOUTH';
            elsif  v_intake_node_id=15 then
               v_column :='ABORIGINALOTHER';
            elsif  v_intake_node_id=16 then
               v_column :='PREGNANT';
            elsif  v_intake_node_id=17 then
               v_column :='DISCLOSEDABUSE';
            elsif  v_intake_node_id=18 then
               v_column :='DISABILITY';
            elsif  v_intake_node_id=19 then
               v_column :='OBSERVEDABUSE';
            elsif  v_intake_node_id=20 then
               v_column :='DISCLOSEDMENTALISSUE';
            elsif  v_intake_node_id=21 then
               v_column :='POORHYGIENE';
            elsif  v_intake_node_id=22 then
               v_column :='OBSERVEDMENTALISSUE';
            elsif  v_intake_node_id=23 then
               v_column :='DISCLOSEDALCOHOLABUSE';
            elsif  v_intake_node_id=24 then
               v_column :='OBSERVEDALCOHOLABUSE';
            elsif  v_intake_node_id=25 then
               v_column :='BIRTHCERTIFICATE';
            elsif  v_intake_node_id=26 then
               v_column :='BIRTHCERTIFICATEYN';
            elsif  v_intake_node_id=27 then
               v_column :='SIN';
            elsif  v_intake_node_id=28 then
               v_column :='SINYN';
            elsif  v_intake_node_id=29 then
               v_column :='HEALTHCARDNO';
            elsif  v_intake_node_id=30 then
               v_column :='HEALTHCARDNOYN';
            elsif  v_intake_node_id=31 then
               v_column :='DRIVERLICENSENO';
            elsif  v_intake_node_id=32 then
               v_column :='DRIVERLICENSENOYN';
            elsif  v_intake_node_id=33 then
               v_column :='CITIZENCARDNO';
            elsif  v_intake_node_id=34 then
               v_column :='CITIZENCARDNOYN';
            elsif  v_intake_node_id=35 then
               v_column :='NATIVERESERVENO';
            elsif  v_intake_node_id=36 then
               v_column :='NATIVERESERVENOYN';
            elsif  v_intake_node_id=37 then
               v_column :='VETERANNO';
            elsif  v_intake_node_id=38 then
               v_column :='VETERANNOYN';
            elsif  v_intake_node_id=39 then
               v_column :='RECORDLANDING';
            elsif  v_intake_node_id=40 then
               v_column :='RECORDLANDINGYN';
            elsif  v_intake_node_id=41 then
               v_column :='LIBRARYCARD';
            elsif  v_intake_node_id=42 then
               v_column :='LIBRARYCARDYN';
            elsif  v_intake_node_id=43 then
               v_column :='IDOTHER';
            elsif  v_intake_node_id=44 then
              v_column :='INCOME';
            elsif  v_intake_node_id=45 then
               v_column :='INCOMEWORKERNAME1';
            elsif  v_intake_node_id=46 then
               v_column :='INCOMEWORKERPHONE1';
            elsif  v_intake_node_id=47 then
               v_column :='INCOMEWORKEREMAIL1';
            elsif  v_intake_node_id=48 then
               v_column :='INCOMEWORKERNAME2';
            elsif  v_intake_node_id=49 then
               v_column :='INCOMEWORKERPHONE2';
            elsif  v_intake_node_id=50 then
               v_column :='INCOMEWORKEREMAIL2';
            elsif  v_intake_node_id=51 then
               v_column :='INCOMEWORKERNAME3';
            elsif  v_intake_node_id=52 then
               v_column :='INCOMEWORKERPHONE3';
            elsif  v_intake_node_id=53 then
               v_column :='INCOMEWORKEREMAIL3';
            elsif  v_intake_node_id=54 then
               v_column :='LIVEDBEFOREOTHER';
            elsif  v_intake_node_id=55 then
               v_column :='ORIGINALCOUNTRY';
            elsif  v_intake_node_id=56 then
               v_column :='COMMENTS';
            elsif  v_intake_node_id=57 then
               v_column :='LANGUAGE';
            elsif  v_intake_node_id=58 then
               v_column :='VAW';
            elsif  v_intake_node_id=59 then
               v_column :='INSHELTERBEFORE';
            elsif  v_intake_node_id= 61 then
               v_column :='REASONFORSERVICE';
           end if;

       if v_insertYN='Y' then
           v_sql := 'insert into report_intake(intake_id,' || v_column || ') values (' || v_intake_id || ',' || '''' || v_value || ''')';
       else
           v_sql := 'update report_intake set ' || v_column || '= ''' || v_value || ''' where intake_id=' || v_intake_id;
       end if;

       begin
           execute immediate v_sql;
       exception
           when others then
           null;
           dbms_output.put_line(v_sql);
       end;
       return;
	END;
/

prompt
prompt Creating trigger TRI_bed_check_time
prompt ===================================
prompt
create or replace trigger "TRI_bed_check_time"
after UPDATE or delete ON bed_check_time
REFERENCING NEW AS NEW OLD AS OLD
for each row
DECLARE
 v_action VARCHAR2(1) :='';
BEGIN
  BEGIN
    IF UPDATING THEN
      v_action:='U';
    ELSIF DELETING THEN
      v_action:='D';
   end if;
      INSERT INTO QUATROSHELTER_LOG.bed_check_time_log VALUES
      (
      :old.bed_check_time_id,
      :old.program_id,
      :old.bed_check_time,
      :old.lastupdateuser,
      :old.lastupdatedate,
      QUATROSHELTER_LOG.SEQ_BED_CHECK_TIME_LOG.NEXTVAL,
      v_action
      );
 EXCEPTION
          WHEN OTHERS THEN NULL;
 END;
END TRI_BED_CHECK_TIME;
/


spool off
