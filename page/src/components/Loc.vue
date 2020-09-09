<template>
    <div>
        <div class="block" v-loading="loading"
             element-loading-text="加载中..........."
             element-loading-background="rgba(255, 255, 255, 0.7)">
            <el-button style="float: right"  icon="el-icon-upload"
                        size="mini"
                        type="text"
                        @click="batchUpload"
            >批量上传</el-button>
            <el-table
                    :data="tableData.filter(data => !search || data.fileName.toLowerCase().includes(search.toLowerCase()))"
                    stripe
                    style="width: 100%">
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
                        <el-button icon="el-icon-message"
                                   size="mini"
                                   type="danger"
                                   @click="fetchFileData(scope.$index, scope.row)">查看
                        </el-button>
                        <el-button icon="el-icon-upload"
                                   size="mini"
                                   type="danger"
                                   @click="upload(scope.$index, scope.row)">上传
                        </el-button>
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
    let id = 100;
    export default {

        created() {
            this.loadInterfaces()
        },
        methods: {
            batchUpload() {
                var that = this
                this.loading = true
                this.$http.post(that.GLB.batch_upload).then(response => {
                    if (response.body.message) {
                        that.loading = false
                        that.$notify({
                            title: '批量上传结果',
                            message: response.body.message,
                            type: 'error'
                        });
                    } else {
                        that.loading = false
                        that.$notify({
                            title: '批量上传结果',
                            message: '批量上传失败，服务器异常',
                            type: 'error'
                        });
                    }

                }, err => {
                    that.loading = false
                    that.$notify({
                        title: '异常',
                        message: '加载错误',
                        type: 'error'
                    });
                });
            },
            fetchFileData(index, row) {
                var that = this
                if (row.fileName.endWith(".json")) {
                    this.loading = true
                    this.fileName = row.fileName;
                    this.$http.get(that.GLB.mock_get_loc_data, {params: {fileName: that.fileName}}).then(response => {
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
                this.$http.get(that.GLB.mock_get_loc_dic).then(response => {
                    that.appName = that.GLB.appName
                    let arr = JSON.parse(JSON.stringify(response.body))
                    arr.forEach(function (e) {
                        that.tableData.push({
                            "fileName": e
                        });
                    })
                    that.loading = false
                }, err => {
                    that.loading = false
                    that.$notify({
                        title: '异常',
                        message: '加载错误',
                        type: 'error'
                    });
                });
            },

            upload(index, row) {
                var that = this
                this.loading = true
                if (row.fileName.endWith(".json")) {
                    this.$http.post(that.GLB.mock_upload, {fileName: row.fileName}).then(response => {
                        if (response.body.message) {
                            that.loading = false
                            that.$notify({
                                title: '上传结果',
                                message: response.body.message,
                                type: 'success'
                            });
                        } else {
                            that.loading = false
                            that.$notify({
                                title: '失败',
                                message: '上传失败，服务器异常',
                                type: 'error'
                            });
                        }

                    }, err => {
                        that.loading = false
                        that.$notify({
                            title: '异常',
                            message: '加载错误',
                            type: 'error'
                        });
                    });
                }
            },
            getDateTick() {
                var date = new Date();
                var year = date.getYear()
                var month = date.getMonth() + 1;
                var day = date.getDate();
                var hour = date.getHours();
                var min = date.getMinutes();
                year = year < 2000 ? year + 1900 : year
                month = (month < 10 ? "0" + month : month);
                day = (day < 10 ? "0" + day : day);
                hour = (hour < 10 ? "0" + hour : hour);
                min = (min < 10 ? "0" + min : min);
                var mydate = (year.toString().substr(2, 2) + month.toString() + day.toString() + hour.toString() + min.toString());
                return mydate;
            }
        },
        data() {
            return {
                dialogFormVisible: false,
                tableData: [],
                search: '',
                loading: false,
                filterText: '',
                interfaces: [],
                jsonInputData: "",
                appName: "",
                fileName: ""
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
        margin: 0px 0px 0px 0px;
        width: 99%;
        height: 80vh;
    }
</style>