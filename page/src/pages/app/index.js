import Vue from 'vue';
import ElementUI from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from '../../components/App.vue';
import Router from 'vue-router'
import vueResourceServer from '../../utils/hserver'
import GLOBAL from '../../utils/common'
import Invoke from '../../components/Invoke'
import Mock from '../../components/Mock'
import Ftp from '../../components/Ftp'
import Loc from '../../components/Loc'
import 'bootstrap/dist/css/bootstrap.min.css'
import 'bootstrap/dist/js/bootstrap.min.js'
Vue.use(ElementUI);
Vue.use(Router)
Vue.use(vueResourceServer)
Vue.prototype.GLB = GLOBAL
String.prototype.endWith=function(str){
  if(str==null||str==""||this.length==0||str.length>this.length)
    return false;
  if(this.substring(this.length-str.length)==str)
    return true;
  else
    return false;
  return true;
}
var router = new Router({
  routes: [
    {
      path: '/',
      name: 'Loc',
      redirect: '/c4'
    },
    {
      path: '/c1',
      name: 'Mock',
      component: Mock
    },
    {
      path: '/c2',
      name: 'Invoke',
      component: Invoke
    },
    {
      path: '/c3',
      name: 'Ftp',
      component: Ftp
    },
    {
      path: '/c4',
      name: 'Loc',
      component: Loc
    }
  ]
})

new Vue({
  el: '#app',
  router,
  render: h => h(App)
});
