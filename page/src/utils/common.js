import $ from 'jquery'

/**
 * 公共方法以及变量写在whole内
 * 命名建议（方法 fn_开头）
 */
export default {
  //=======================================================全局变量===========================================
  invoke_get_beans_rul: 'getBeanName.json',
  invoke_get_methods_rul: 'getMethods.json',
  invoke_get_params_url: 'getParams.json',
  invoke_call_url: 'call.json',
  get_network_workday_url:'getNetWorkDay.json',
  host_info_url:'hostName.json',
  login_info_url:'loginStatus.json',
  mock_get_all:'mockAllData.json',
  mock_update:'mockUpdate.json',
  mock_get_loc_dic:'getLocDic.json',
  mock_get_loc_data:'getLocData.json',
  mock_upload:'mockUpload.json',
  batch_upload:'batchUpload.json',
  mock_download:'mockDownload.json',
  batch_download:'batchDownload.json',
  mock_get_remote_data:'getRemoteData.json',
  mock_get_remote_dic:'getRemoteDic.json',
  mock_get_remote_user:'getRemoteUser.json',
  appName: '',
  userName:'',
  //=======================================================全局函数===========================================


  /**
   * 日期时间格式化
   * @param date
   * @returns {string}
   */
  formatDate: function (date) {
    var fmt = "yyyy-MM-dd";
    var o = {
      "M+": date.getMonth() + 1, //月份
      "d+": date.getDate(), //日
      "h+": date.getHours(), //小时
      "m+": date.getMinutes(), //分
      "s+": date.getSeconds(), //秒
      "q+": Math.floor((date.getMonth() + 3) / 3), //季度
      "S": date.getMilliseconds() //毫秒
    };
    if (/(y+)/.test(fmt)) fmt = fmt.replace(RegExp.$1, (date.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o)
      if (new RegExp("(" + k + ")").test(fmt)) fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
    return fmt;
  },
  not_null: function (obj) {
    if(typeof obj === "string"){
      if(obj === '' || obj === undefined || obj === null){
        return false
      }
      return true
    }
    if(typeof obj === "object"){
      if (Object.keys(object).length === 0) {
        return false
      }
      return true
    }
  }
}
