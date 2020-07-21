<style scoped>
  .el-header{
    background-color: rgba(41, 23, 43, 0.9);
    color: #333;
    text-align: center;
    line-height: 60px;
    padding: 0 0px;
  }
  .el-footer {
    background-color: rgba(41, 23, 43, 0.9);
    color: #333;
    text-align: center;
    line-height: 12px;
    padding: 0 0px;
  }

  .el-aside {
    background-color: #545c64;
    color: #333;
    text-align: center;
    line-height: 200px;
  }

  .el-main {
    background-color: #E9EEF3;
    color: #333;
  }

  body > .el-container {
    margin-bottom: 40px;
  }

</style>

<template>
  <div>
    <el-container>
      <el-header>
        <span  class="el-icon-hot-water" style=" line-height:60px;float: left;margin-left: 30px;
color: rgb(251, 251, 251);
    font-size: 17px;
    font-weight: 500;

"> 本地控制台&nbsp;&nbsp;{{appName}}
        </span>
        <span class="el-icon-user" style=" line-height:60px;float: right;margin-right: 30px; color: #eee;">{{userName}}</span>
      </el-header>

      <el-container>
        <el-aside width="200px">
          <el-menu
                  :default-active="activeIndex"
                  class="el-menu-demo"
                  mode="vertical"
                  @select="handleSelect"
                  background-color="#545c64"
                  text-color="#fff"
                  active-text-color="#ffd04b"
          >
            <el-menu-item index="c2">
              <template slot="title">
                <i class="el-icon-s-promotion"></i>
                <span>方法调用</span>
              </template>
            </el-menu-item>
            <el-menu-item index="c4">
              <template slot="title">
                <i class="el-icon-upload"></i>
                <span>本地上传</span>
              </template>
            </el-menu-item>
            <el-menu-item index="c3">
              <template slot="title">
                <i class="el-icon-download"></i>
                <span>远程下载</span>
              </template>
            </el-menu-item>
          </el-menu>
        </el-aside>
        <el-main><router-view></router-view></el-main>
      </el-container>
      <el-footer>
        <span style="text-align: center;color: #eee;">
          <pre style="color: #eee;">
                    __
  __ _  ___  ____  / /__
 /  ' \/ _ \/ __/ /  '_/
/_/_/_/\___/\__/ /_/\_\
          </pre>
        </span>
      </el-footer>
    </el-container>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        activeIndex: 'c4',
        appName: '',
        userName:'',
        hostIp: '127.0.0.1'
      };
    },
    methods: {
      handleSelect(key, keyPath) {
        console.log(key, keyPath);
          this.$router.push(key).catch(err => {
            console.log(err)
          })
      }
    },
    created(){
      var path = this.$route.path
      this.activeIndex = path.substring(1,path.length)
    },
    mounted() {
      var that  = this;
      this.$http.get(this.GLB.host_info_url).then(res=> {
        if(res.body.hostName) {
          that.hostIp = res.body.hostName;
          that.GLB.appName = res.body.appName;
          that.appName = res.body.appName;
          that.GLB.userName = res.body.userName;
          that.userName = res.body.userName;
        } else{
          that.hostIp = '1.1.1.1';
          that.userName = 'spring';
        }
      })
    }
  }
</script>

<style scoped>

</style>
