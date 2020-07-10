<style src="../styles/css/jquery.json-viewer.css"></style>
<style scoped>
    body {
        background-color: #F7F7F7
    }
</style>

<template>
    <div>
        <el-row>
            <el-col :span="10">
                <div>
                    <textarea id="json-input" v-text="jsonInputData" rows="15"
                              style="height: 80vh; width: 100%;magin:0px;padding-left: 5px">
                    </textarea>
                </div>
            </el-col>
            <el-col :span="14">
                <div>
                    <pre id="json-renderer"
                         style="height: 80vh;width: 95%;overflow: auto;margin:0px;padding-left: 30px"></pre>
                </div>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="24" style="padding-left: 400px;padding-top: 20px">
                <el-button type="info" style="margin-left: 30px" @click="convertView">格式化</el-button>
                <el-button type="info" style="margin-left: 30px" @click="jsonViewClick">提交修改</el-button>
                <el-button type="info" style="margin-left: 30px" @click="collapse">收缩</el-button>
                <el-button type="info" style="margin-left: 30px" @click="expande">展开</el-button>
            </el-col>
        </el-row>

    </div>
</template>
<script>
    import "../styles/js/jquery.json-viewer.js"

    export default {
        data() {
            return {
                jsonInputData: ""
            }
        },
        created() {
            this.fatchData()
        },
        methods: {
            fatchData() {
                var that = this
                this.$http.get(that.GLB.mock_get_all).then(response => {
                    $("#json-input").text(JSON.stringify(response.body))
                    that.convertView()
                });
            },

            jsonViewClick() {
                var that = this
                this.$http.post(that.GLB.mock_update, {json: $('#json-input').val()}).then(response => {
                    that.convertView()
                });
            },
            convertView() {
                var input = eval('(' + $('#json-input').val() + ')');
                $('#json-input').val(JSON.stringify($('#json-input').val(), null, 4))
                var options = {
                    collapsed: false
                };
                $('#json-renderer').jsonViewer(input, options);
            },
            collapse() {
                var input = eval('(' + $('#json-input').val() + ')');
                var options = {
                    collapsed: true
                };
                $('#json-renderer').jsonViewer(input, options);
            },
            expande() {
                var input = eval('(' + $('#json-input').val() + ')');
                var options = {
                    collapsed: false
                };
                $('#json-renderer').jsonViewer(input, options);
            }
        },
    }
</script>