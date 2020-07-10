<style scoped>
  .el-row {
    margin-bottom: 20px;}
</style>

<template>
  <div>

    <el-row :gutter="20">
      <el-col :span="4" style="text-align: right"><span>Spring Bean名称</span></el-col>
      <el-col :span="20">
        <el-select v-model="beanName" @change="chooseBean" style="width: 90%; " filterable placeholder="请选择">
          <el-option
            v-for="item in beanOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>

      </el-col>
    </el-row>

    <el-row :gutter="20">
      <el-col :span="4" style="text-align: right"><span>方法名</span></el-col>
      <el-col :span="20">
        <el-select v-model="methodName" @change="chooseMethod" style="width: 90%; " filterable placeholder="请选择">
          <el-option
            v-for="item in methodOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-col>
    </el-row>


    <el-row :gutter="20">
      <el-col :span="4" style="text-align: right"><span>方法入参</span></el-col>
      <el-col :span="8">
        <el-input
          type="textarea"
          :rows="20"
          placeholder="请输入内容"
          v-model="paramTextarea">
        </el-input>
      </el-col>
      <el-col :span="2" style="text-align: right"><span>结果</span></el-col>
      <el-col :span="8">
        <el-input
                  type="textarea"
                  :rows="20"
                  placeholder=""
                  v-model="resultTextarea">
        </el-input>
      </el-col>
    </el-row>


    <el-row :gutter="20">
      <el-col :span="4" style="text-align: right"><span>&nbsp;</span></el-col>
      <el-col :span="20">
        <el-button type="primary" @click="methodInvoke">调用</el-button>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  export default {
    data() {
      return {
        resultTextarea:'',
        paramTextarea: '',
        beanName: '',
        methodName: '',
        textarea:'',
        beanOptions:[],
        methodOptions:[]
      }
    },
    mounted () {
      this.getBeanNames()
    },
    methods: {
      methodInvoke() {
        var that = this
        this.$http.post(that.GLB.invoke_call_url,{
          beanName: that.beanName,
          methodName: that.methodName,
          params: that.paramTextarea
        }).then(response => {
          that.resultTextarea = JSON.stringify(response.body, null, 4)
        });
      },
      chooseMethod(value) {
        var that = this
        this.$http.get(that.GLB.invoke_get_params_url,{params: {beanName: that.beanName,methodName: value}}).then(response => {
          that.paramTextarea = JSON.stringify(response.body, null, 4)
          that.resultTextarea=""
        });
      },
      chooseBean(value) {
        var that = this
        this.$http.get(that.GLB.invoke_get_methods_rul,{params: {beanName: value}}).then(response => {
          var body = $.map(response.body, function(item){
            return {value: item, label: item}
          })
          that.methodOptions = body
          that.methodName = ""
          that.paramTextarea=""
          that.resultTextarea=""
        });
      },
      getBeanNames() {
        var that = this
        // GET /someUrl
        this.$http.get(that.GLB.invoke_get_beans_rul).then(response => {
            var body = $.map(response.body.beanNames, function(item){
              return {value: item, label: item}
            })
            that.beanOptions = body
        });

        // this.$http.get(that.GLB.invoke_get_beans_rul).then(json=> {
        //   var data = $.map(json.beanNames, function(item){
        //     return {value: item, label: item}
        //   })
        //   that.beanOptions = data
        // })
      }
    },
  }
</script>

<style scoped>

</style>
