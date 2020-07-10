<template>
    <div>

    <div class="block" v-loading="loading"
         element-loading-text="加载中..........."
         element-loading-background="rgba(255, 255, 255, 0.7)">


        <el-table
                :data="userData.filter(data => !search || data.erp.toLowerCase().includes(search.toLowerCase()))"
                stripe
                style="width: 100%"
                v-show="showUser"
        >
            <el-table-column
                    label="ID"
                    type="index">
            </el-table-column>
            <el-table-column
                    prop="date"
                    label="人员空间">
                <template slot-scope="scope">
                    <i class="el-icon-sugar"></i>
                    <a href="#" @click="fetchByUser(scope.row.erp)">{{ scope.row.erp }}</a>
                </template>
            </el-table-column>

            <el-table-column
                    align="right">
                <template slot="header" slot-scope="scope">
                    <el-input
                            v-model="search"
                            size="mini"
                            placeholder="输入关键字搜索"/>
                </template>
            </el-table-column>

        </el-table>

        <el-button  icon="el-icon-back"
                    size="mini"
                    type="danger"
                    @click="showUser = true"
                    v-show="showUser == false"
        >返回</el-button>
        <el-table
                :data="tableData.filter(data => !search || data.fileName.toLowerCase().includes(search.toLowerCase()))"
                stripe
                style="width: 100%"
                v-show="showUser == false"
        >
            <el-table-column
                    label="ID"
                    type="index">
            </el-table-column>
            <el-table-column
                    prop="date"
                    label="方法标识">
                <template slot-scope="scope">
                    <i class="el-icon-sugar"></i>
                    <a href="#" @click="fetchFileData(scope.$index, scope.row)">{{ scope.row.fileName }}</a>
                </template>
            </el-table-column>

            <el-table-column
                    align="right">
                <template slot="header" slot-scope="scope">
                    <el-input
                            v-model="search"
                            size="mini"
                            placeholder="输入关键字搜索"/>
                </template>
                <template slot-scope="scope">
                    <el-button  icon="el-icon-message"
                                size="mini"
                                type="danger"
                                @click="fetchFileData(scope.$index, scope.row)"
                                >查看</el-button>
                    <el-button  icon="el-icon-upload"
                                size="mini"
                                type="danger"
                                @click="download(scope.$index, scope.row)"
                                >下载</el-button>
                </template>
            </el-table-column>

        </el-table>
    </div>
        <el-dialog title="JSON文件" footer-padding="10px 30px 15px" :visible.sync="dialogFormVisible" width="60%"
                   min-height="50px" max-height="400px">
            <span style="font-size: 8px">{{fileName}}</span>
            <textarea id="json-input" v-text="jsonInputData" rows="15"
                      style=" width: 100%;magin:0px;padding-left: 5px;margin-top: 20px;font-size: 8px">
                    </textarea>
        </el-dialog>
   </div>
</template>

<script>
    let id= 100;
    export default {

        created() {
            this.loadInterfaces()
        },
        methods: {
            fetchByUser(erp) {
                this.search = ''
                this.choseUser = erp
                this.showUser =  false;
                var that = this
                this.loadding = true
                this.$http.get(that.GLB.mock_get_remote_dic, {params: {user: erp}}).then(response => {
                    that.appName = that.GLB.appName
                    let arr = JSON.parse(JSON.stringify(response.body))
                    that.tableData = []
                    arr.forEach(function (e) {
                        that.tableData.push({
                            "fileName": e
                        });
                    })
                    that.loadding = false
                },err=> {
                    that.loadding = false
                    that.$notify({
                        title: '异常',
                        message: '加载错误',
                        type: 'error'
                    });
                });

            },
            fetchFileData(index,row) {
                var that = this
                if (row.fileName.endWith(".json")) {
                    this.loading = true
                    this.fileName = row.fileName;
                    this.$http.get(that.GLB.mock_get_remote_data, {params: {user:that.choseUser,fileName: that.fileName}}).then(response => {
                        that.dialogFormVisible = true
                        that.jsonInputData = JSON.stringify(response.body, null, 4)
                        that.loading = false
                    });
                } else {
                    that.$notify({
                        title: '错误',
                        message: '只支持json格式预览',
                        type: 'error'
                    });
                }
            },
            loadInterfaces() {
                var that = this
                this.loading = true
                this.$http.get(that.GLB.mock_get_remote_user).then(response => {
                    that.appName = that.GLB.appName
                    let arr = JSON.parse(JSON.stringify(response.body))
                    arr.forEach(function (e) {
                        that.userData.push({
                            "erp": e
                        });
                    })
                    that.loading = false
                },err=> {
                    that.loadding = false
                    that.$notify({
                        title: '异常',
                        message: '加载错误',
                        type: 'error'
                    });
                });
            },
            download(index, row) {
                var that = this
                if (row.fileName.endWith(".json")) {
                    this.loading = true
                    this.fileName = row.fileName;
                    this.$http.get(that.GLB.mock_download, {params: {user:that.choseUser,fileName: that.fileName}}).then(response => {
                        that.$notify({
                            title: '下载成功',
                            message: '下载成功',
                            type: 'error'
                        });
                        that.loading = false
                    });
                } else {
                    that.$notify({
                        title: '错误',
                        message: '只支持json格式预览',
                        type: 'error'
                    });
                }
            },
            getDateTick() {
                var date= new Date();
                var year = date.getYear()
                var month=date.getMonth()+1;
                var day = date.getDate();
                var hour = date.getHours();
                var min = date.getMinutes();
                year = year < 2000 ? year + 1900 : year
                month =(month<10 ? "0"+month:month);
                day =(day<10 ? "0"+day:day);
                hour =(hour<10 ? "0"+hour:hour);
                min =(min<10 ? "0"+min:min);
                var mydate = (year.toString().substr(2, 2)+month.toString()+day.toString()+hour.toString()+min.toString());
                return mydate;
            }
        },
        watch: {
            filterText(val) {
                this.$refs.tree2.filter(val);
            }
        },
        data() {
            return {
                choseUser: this.GLB.userName,
                showUser:true,
                userData:[],
                tableData: [],
                search: '',
                disableSaveBtn:true,
                ruleForm: {
                    name:'',
                    textarea:''
                },
                rules: {
                    name: [
                        {required: true, message: '请输入文件名称', trigger: 'blur'}
                    ],
                    textarea: [
                        {required: true, message: '请输入文件内容', trigger: 'blur'}
                    ]
                },
                dialogFormVisible:false,
                loading: true,
                filterText: '',
                interfaces: [],
                jsonInputData: "",
                appName:"",
                fileName:"hello.json"
            }
        },
    };
</script>

<style>
    .custom-tree-node {
        flex: 1;
        display: flex;
        align-items: center;
        justify-content: space-between;
        font-size: 14px;
        padding-right: 8px;
    }
    .block {
        width: 99%;
        height: 80vh;
    }
</style>