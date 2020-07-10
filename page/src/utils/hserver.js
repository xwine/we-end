import vueResource from 'vue-resource'
import VueResourceMock from 'vue-resource-mock'
import MockData from './mock-data'
export default{
  install(Vue,options){
    Vue.use(vueResource)
    if (process.env.NODE_ENV === 'development') {
         // Vue.use(VueResourceMock, MockData, /* { silent: true/false } */) // after use vue-resource
    }
    Vue.http.options.emulateJSON = true
    Vue.http.options.emulateHTTP = true;
    Vue.http.interceptors.push(function(request, next) {
      request.url = process.env.API_HOST+request.url
      console.log('新请求 >>>>>>>>>>>'+JSON.stringify(request))
      next(function(response) {
      });
    });
  }
}
