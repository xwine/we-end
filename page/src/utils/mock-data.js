import G from './common'
export default {

  ['POST *'+G.workday_update_url] (pathMatch, query, request) {
    let body = {"id":44,"date":"2018-08-27","isWorkDay":false}
    return {
      body: body,
      status: 200,
      statusText: 'OK',
      headers: { /*headers*/ },
      deylay: 500, // millisecond
    }
  },
  ['GET *'+G.workday_url] (pathMatch, query, request) {
    let body = [{"allDay":true,"backgroundColor":"#6aba49","color":"#6aba49","id":"2018-09-15","start":"2018-09-15","textAlign":"left","textColor":"#fff","title":"休"},{"allDay":true,"backgroundColor":"#6aba49","color":"#6aba49","id":"2018-09-07","start":"2018-09-07","textAlign":"left","textColor":"#fff","title":"休"},{"allDay":true,"backgroundColor":"#6aba49","color":"#6aba49","id":"2018-09-06","start":"2018-09-06","textAlign":"left","textColor":"#fff","title":"休"},{"allDay":true,"backgroundColor":"#6aba49","color":"#6aba49","id":"2018-09-13","start":"2018-09-13","textAlign":"left","textColor":"#fff","title":"休"},{"allDay":true,"backgroundColor":"#6aba49","color":"#6aba49","id":"2018-09-08","start":"2018-09-08","textAlign":"left","textColor":"#fff","title":"休"}]
    return {
      body: body,
      status: 200,
      statusText: 'OK',
      headers: { /*headers*/ },
      deylay: 500, // millisecond
    }
  },
  ['GET *'+G.invoke_get_beans_rul](pathMatch,query,request){
    let body = {"beanNames":["org.springframework.beans.factory.config.PropertyPlaceholderConfigurer#0","popThemisDataSource","areaLimitTaskDataSource","popwareExtDataSource","searchClickStreamMongoConfig","wareNotesDraftBoxDriver","wareNotesDraftBoxMongoClient","monitoringAdvisor","org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator#0","springDataSourceBeanPostProcessor","org.springframework.context.annotation.ConfigurationClassPostProcessor.importAwareProcessor"]}
    return {
    body: body,
    status: 200,
    statusText: 'OK',
    headers: { /*headers*/ },
    deylay: 500, // millisecond
  }
  },
  ['GET *'+G.invoke_get_methods_rul](pathMatch,query,request){
    let body = {"public_TempCompletePage_findTempCompletePage(TempCompleteQueryExport_tempCompleteQueryExport)":"public TempCompletePage findTempCompletePage(TempCompleteQueryExport tempCompleteQueryExport)","public_Integer_findWaitProcessCount(WaitProcessQueryExport_waitProcessQueryExport)":"public Integer findWaitProcessCount(WaitProcessQueryExport waitProcessQueryExport)","public_transient_String_execute(String_json,_Object[]_args)":"public transient String execute(String json, Object[] args)","public_final_native_Class_getClass()":"public final native Class getClass()","public_final_native_void_notifyAll()":"public final native void notifyAll()","public_final_void_wait()":"public final void wait()","public_String_toString()":"public String toString()","public_Long_findTotalWaitAuditCount(ServiceCountQueryExport_waitAuditCountQueryExport)":"public Long findTotalWaitAuditCount(ServiceCountQueryExport waitAuditCountQueryExport)","public_final_native_void_wait(long_)":"public final native void wait(long )","public_WaitProcessPageExport_findWaitProcessPage(WaitProcessQueryExport_waitProcessQueryExport)":"public WaitProcessPageExport findWaitProcessPage(WaitProcessQueryExport waitProcessQueryExport)","public_final_native_void_notify()":"public final native void notify()","public_Integer_findTempCompletePageCount(TempCompleteQueryExport_tempCompleteQueryExport)":"public Integer findTempCompletePageCount(TempCompleteQueryExport tempCompleteQueryExport)","public_Integer_queryServicePageCount(ServiceQueryExport_serviceQueryExport)":"public Integer queryServicePageCount(ServiceQueryExport serviceQueryExport)","public_final_void_wait(long_,_int_)":"public final void wait(long , int )","public_boolean_equals(Object_)":"public boolean equals(Object )","public_WaitAuditApplysPage_findWaitAuditApplys(WaitAuditQueryExport_waitAuditQueryExport)":"public WaitAuditApplysPage findWaitAuditApplys(WaitAuditQueryExport waitAuditQueryExport)","public_ServicePageExport_queryServicePage(ServiceQueryExport_serviceQueryExport)":"public ServicePageExport queryServicePage(ServiceQueryExport serviceQueryExport)","public_native_int_hashCode()":"public native int hashCode()"}
    return {
    body: body,
    status: 200,
    statusText: 'OK',
    headers: { /*headers*/ },
    deylay: 500, // millisecond
  }
  },
  ['GET *'+G.invoke_get_params_url](pathMatch,query,request){
    let body = [{"afsApplyTimeBegin":"","afsApplyTimeEnd":"","afsCategoryIdPop":"","afsServiceId":"","afsServiceProcessResult":"","buId":"","customerPin":"","messageStatus":"","operatorInfoExport":"","orderId":"","orderType":"","pageIndex":0,"pageSize":10,"searchType":"","verificationCode":"","wareId":"","waybill":""}]
    return {
    body: body,
    status: 200,
    statusText: 'OK',
    headers: { /*headers*/ },
    deylay: 500, // millisecond
  }
  },
  ['POST *'+G.invoke_call_url](pathMatch,query,request){
    let body = [{"verificationCode":"","wareId":"","waybill":""}]
    return {
    body: body,
    status: 200,
    statusText: 'OK',
    headers: { /*headers*/ },
    deylay: 500, // millisecond
  }
  },
  ['GET *'+G.mock_get_all](pathMatch,query,request){
    let body = [{"verificationCode":"","wareId":"","waybill":""}]
    return {
    body: body,
    status: 200,
    statusText: 'OK',
    headers: { /*headers*/ },
    deylay: 500, // millisecond
  }
  },
  ['GET *'+G.mock_update](pathMatch,query,request){
    let body = [{"success":"111"}]
    return {
    body: body,
    status: 200,
    statusText: 'OK',
    headers: { /*headers*/ },
    deylay: 500, // millisecond
  }
  },
  ['GET *'+G.login_info_url](pathMatch,query,request){
    let body = {"fullname":"张三"}
    return {
    body: body,
    status: 200,
    statusText: 'OK',
    headers: { /*headers*/ },
    deylay: 500, // millisecond
  }
  },
  ['GET *'+G.host_info_url](pathMatch,query,request){
    let body = {"hostName":"127.0.0.1"}
    return {
    body: body,
    status: 200,
    statusText: 'OK',
    headers: { /*headers*/ },
    deylay: 500, // millisecond
  }
  }
}
