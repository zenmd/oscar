--------------------------------------------------------
-- Export file for user QUATROSHELTER_LOG             --
-- Created by Administrator on 11/17/2008, 1:08:03 PM --
--------------------------------------------------------

spool QutroShelterDB_LOG.log

prompt
prompt Creating sequence SEQ_ADMISSION_LOG
prompt ===================================
prompt
create sequence SEQ_ADMISSION_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 121
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_AGENCY_LOG
prompt ================================
prompt
create sequence SEQ_AGENCY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_APP_MODULE_LOG
prompt ====================================
prompt
create sequence SEQ_APP_MODULE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ATTACHMENT_LOG
prompt ====================================
prompt
create sequence SEQ_ATTACHMENT_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_BED_CHECK_TIME_LOG
prompt ========================================
prompt
create sequence SEQ_BED_CHECK_TIME_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_BED_DEMOGRAPHIC_HIS_LOG
prompt =============================================
prompt
create sequence SEQ_BED_DEMOGRAPHIC_HIS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_BED_DEMOGRAPHIC_LOG
prompt =========================================
prompt
create sequence SEQ_BED_DEMOGRAPHIC_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 121
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_BED_DEMOGRAPHIC_STATUS_LOG
prompt ================================================
prompt
create sequence SEQ_BED_DEMOGRAPHIC_STATUS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_BED_LOG
prompt =============================
prompt
create sequence SEQ_BED_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_BED_TYPE_LOG
prompt ==================================
prompt
create sequence SEQ_BED_TYPE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CASEMGMT_CPP_LOG
prompt ======================================
prompt
create sequence SEQ_CASEMGMT_CPP_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CASEMGMT_ISSUE_LOG
prompt ========================================
prompt
create sequence SEQ_CASEMGMT_ISSUE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CASEMGMT_NOTE_LOG
prompt =======================================
prompt
create sequence SEQ_CASEMGMT_NOTE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CLIENT_IMAGE_LOG
prompt ======================================
prompt
create sequence SEQ_CLIENT_IMAGE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CLIENT_REFERRAL_LOG
prompt =========================================
prompt
create sequence SEQ_CLIENT_REFERRAL_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 681
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_COMPLAINT_LOG
prompt ===================================
prompt
create sequence SEQ_COMPLAINT_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_CONSENT_DETAIL_LOG
prompt ========================================
prompt
create sequence SEQ_CONSENT_DETAIL_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_DEMOGRAPHIC_LOG
prompt =====================================
prompt
create sequence SEQ_DEMOGRAPHIC_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 181
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_DEMOGRAPHIC_MERGED_LOG
prompt ============================================
prompt
create sequence SEQ_DEMOGRAPHIC_MERGED_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_DENCOUNTERFORM_LOG
prompt ========================================
prompt
create sequence SEQ_DENCOUNTERFORM_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_DOCUMENT_LOG
prompt ==================================
prompt
create sequence SEQ_DOCUMENT_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ECHART_LOG
prompt ================================
prompt
create sequence SEQ_ECHART_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ENCOUNTERTEMPLATE_LOG
prompt ===========================================
prompt
create sequence SEQ_ENCOUNTERTEMPLATE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ENCOUNTER_LOG
prompt ===================================
prompt
create sequence SEQ_ENCOUNTER_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_FACILITY_LOG
prompt ==================================
prompt
create sequence SEQ_FACILITY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_FACILITY_MESSAGE_LOG
prompt ==========================================
prompt
create sequence SEQ_FACILITY_MESSAGE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_HEALTH_SAFETY_LOG
prompt =======================================
prompt
create sequence SEQ_HEALTH_SAFETY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_INCIDENT_LOG
prompt ==================================
prompt
create sequence SEQ_INCIDENT_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 221
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_INTAKE_ANSWER_LOG
prompt =======================================
prompt
create sequence SEQ_INTAKE_ANSWER_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 6161
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_INTAKE_FAMILY_LOG
prompt =======================================
prompt
create sequence SEQ_INTAKE_FAMILY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 81
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_INTAKE_LOG
prompt ================================
prompt
create sequence SEQ_INTAKE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LOG_LOG
prompt =============================
prompt
create sequence SEQ_LOG_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_ABORIGINAL_LOG
prompt ========================================
prompt
create sequence SEQ_LST_ABORIGINAL_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_ADMISSION_STATUS_LOG
prompt ==============================================
prompt
create sequence SEQ_LST_ADMISSION_STATUS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_BED_TYPE_LOG
prompt ======================================
prompt
create sequence SEQ_LST_BED_TYPE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_CASESTATUS_LOG
prompt ========================================
prompt
create sequence SEQ_LST_CASESTATUS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_COMPLAINT_METHOD_LOG
prompt ==============================================
prompt
create sequence SEQ_LST_COMPLAINT_METHOD_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_COMPLAINT_OUTCOME_LOG
prompt ===============================================
prompt
create sequence SEQ_LST_COMPLAINT_OUTCOME_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_COMPLAINT_SECTION_LOG
prompt ===============================================
prompt
create sequence SEQ_LST_COMPLAINT_SECTION_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_COMPLAINT_SOURCE_LOG
prompt ==============================================
prompt
create sequence SEQ_LST_COMPLAINT_SOURCE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_COMPLAINT_SUBSEC_LOG
prompt ==============================================
prompt
create sequence SEQ_LST_COMPLAINT_SUBSEC_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_COMPONENTOFSERVICE_LOG
prompt ================================================
prompt
create sequence SEQ_LST_COMPONENTOFSERVICE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_COUNTRY_LOG
prompt =====================================
prompt
create sequence SEQ_LST_COUNTRY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_CURSLEEPARRANGE_LOG
prompt =============================================
prompt
create sequence SEQ_LST_CURSLEEPARRANGE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_DISCHARGE_REASON_LOG
prompt ==============================================
prompt
create sequence SEQ_LST_DISCHARGE_REASON_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_DOCUMENTCATEGORY_LOG
prompt ==============================================
prompt
create sequence SEQ_LST_DOCUMENTCATEGORY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_DOCUMENTTYPE_LOG
prompt ==========================================
prompt
create sequence SEQ_LST_DOCUMENTTYPE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_FAMILY_RELATION_LOG
prompt =============================================
prompt
create sequence SEQ_LST_FAMILY_RELATION_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_FIELDTYPE_LOG
prompt =======================================
prompt
create sequence SEQ_LST_FIELDTYPE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_FIELD_CATEGORY_LOG
prompt ============================================
prompt
create sequence SEQ_LST_FIELD_CATEGORY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_GENDER_LOG
prompt ====================================
prompt
create sequence SEQ_LST_GENDER_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_INCIDENT_NATURE_LOG
prompt =============================================
prompt
create sequence SEQ_LST_INCIDENT_NATURE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_INCIDENT_OTHERS_LOG
prompt =============================================
prompt
create sequence SEQ_LST_INCIDENT_OTHERS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_INC_CLIENTISSUES_LOG
prompt ==============================================
prompt
create sequence SEQ_LST_INC_CLIENTISSUES_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_INC_DISPOSITION_LOG
prompt =============================================
prompt
create sequence SEQ_LST_INC_DISPOSITION_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_INTAKEREJECTREASON_LOG
prompt ================================================
prompt
create sequence SEQ_LST_INTAKEREJECTREASON_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_LANGUAGE_LOG
prompt ======================================
prompt
create sequence SEQ_LST_LANGUAGE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_LENGTHOFHOMELESS_LOG
prompt ==============================================
prompt
create sequence SEQ_LST_LENGTHOFHOMELESS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_LIVEDBEFORE_LOG
prompt =========================================
prompt
create sequence SEQ_LST_LIVEDBEFORE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_MESSAGE_TYPE_LOG
prompt ==========================================
prompt
create sequence SEQ_LST_MESSAGE_TYPE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_OPERATOR_LOG
prompt ======================================
prompt
create sequence SEQ_LST_OPERATOR_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_ORGANIZATION_LOG
prompt ==========================================
prompt
create sequence SEQ_LST_ORGANIZATION_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_ORGCD_LOG
prompt ===================================
prompt
create sequence SEQ_LST_ORGCD_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_PROGRAM_TYPE_LOG
prompt ==========================================
prompt
create sequence SEQ_LST_PROGRAM_TYPE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_PROVINCE_LOG
prompt ======================================
prompt
create sequence SEQ_LST_PROVINCE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_REASONFORHOMELESS_LOG
prompt ===============================================
prompt
create sequence SEQ_LST_REASONFORHOMELESS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_REASONFORSERVICE_LOG
prompt ==============================================
prompt
create sequence SEQ_LST_REASONFORSERVICE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_REASONNOADMIT_LOG
prompt ===========================================
prompt
create sequence SEQ_LST_REASONNOADMIT_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_REASON_NOTSIGN_LOG
prompt ============================================
prompt
create sequence SEQ_LST_REASON_NOTSIGN_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_REFERREDBY_LOG
prompt ========================================
prompt
create sequence SEQ_LST_REFERREDBY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_REFERREDTO_LOG
prompt ========================================
prompt
create sequence SEQ_LST_REFERREDTO_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_ROOM_TYPE_LOG
prompt =======================================
prompt
create sequence SEQ_LST_ROOM_TYPE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_SECTOR_LOG
prompt ====================================
prompt
create sequence SEQ_LST_SECTOR_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_SERVICE_RESTRICT_LOG
prompt ==============================================
prompt
create sequence SEQ_LST_SERVICE_RESTRICT_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_SHELTER_LOG
prompt =====================================
prompt
create sequence SEQ_LST_SHELTER_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_SHELTER_STANDARDS_LOG
prompt ===============================================
prompt
create sequence SEQ_LST_SHELTER_STANDARDS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_SOURCEINCOME_LOG
prompt ==========================================
prompt
create sequence SEQ_LST_SOURCEINCOME_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_STATUSINCANADA_LOG
prompt ============================================
prompt
create sequence SEQ_LST_STATUSINCANADA_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_TITLE_LOG
prompt ===================================
prompt
create sequence SEQ_LST_TITLE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_LST_TRANSPORT_TYPE_LOG
prompt ============================================
prompt
create sequence SEQ_LST_TRANSPORT_TYPE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_PMM_LOG
prompt =============================
prompt
create sequence SEQ_PMM_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_PROGRAMCLIENTRESTRICT_LOG
prompt ===============================================
prompt
create sequence SEQ_PROGRAMCLIENTRESTRICT_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_PROGRAM_LOG
prompt =================================
prompt
create sequence SEQ_PROGRAM_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_PROGRAM_OCCUPANCY_LOG
prompt ===========================================
prompt
create sequence SEQ_PROGRAM_OCCUPANCY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_PROGRAM_QUEUE_LOG
prompt =======================================
prompt
create sequence SEQ_PROGRAM_QUEUE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 61
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_PROPERTY_LOG
prompt ==================================
prompt
create sequence SEQ_PROPERTY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_PROVIDER_LOG
prompt ==================================
prompt
create sequence SEQ_PROVIDER_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 41
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_REPORT_LK_REPORTGROUP_LOG
prompt ===============================================
prompt
create sequence SEQ_REPORT_LK_REPORTGROUP_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_REPORT_LOG
prompt ================================
prompt
create sequence SEQ_REPORT_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ROOM_DEMOGRAPHIC_HIS_LOG
prompt ==============================================
prompt
create sequence SEQ_ROOM_DEMOGRAPHIC_HIS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ROOM_DEMOGRAPHIC_LOG
prompt ==========================================
prompt
create sequence SEQ_ROOM_DEMOGRAPHIC_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ROOM_LOG
prompt ==============================
prompt
create sequence SEQ_ROOM_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_ROOM_TYPE_LOG
prompt ===================================
prompt
create sequence SEQ_ROOM_TYPE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_SECROLE_LOG
prompt =================================
prompt
create sequence SEQ_SECROLE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_SECSITE_LOG
prompt =================================
prompt
create sequence SEQ_SECSITE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_SECURITY_LOG
prompt ==================================
prompt
create sequence SEQ_SECURITY_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 21
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_SECUSERROLE_LOG
prompt =====================================
prompt
create sequence SEQ_SECUSERROLE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_SYSTEM_MESSAGE_LOG
prompt ========================================
prompt
create sequence SEQ_SYSTEM_MESSAGE_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TICKLER_COMMENTS_LOG
prompt ==========================================
prompt
create sequence SEQ_TICKLER_COMMENTS_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;

prompt
prompt Creating sequence SEQ_TICKLER_LOG
prompt =================================
prompt
create sequence SEQ_TICKLER_LOG
minvalue 1
maxvalue 999999999999999999999999999
start with 1
increment by 1
cache 20;


spool off
