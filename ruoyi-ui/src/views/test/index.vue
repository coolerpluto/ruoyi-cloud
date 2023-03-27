<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="128px" >
      <el-row>
        <el-form-item label="输入文本" prop="normalText">
          <el-input v-model="queryParams.normalText" placeholder="请输入" clearable style="width: 220px"/>
        </el-form-item>
        <el-form-item label="输入的文本" prop="normalText">
          <span v-text="queryParams.normalText"></span>
        </el-form-item>
      </el-row>
      
      <el-row>
        <el-form-item label="密码框" prop="pwdval">
          <el-input placeholder="请输入密码" v-model="queryParams.pwdval" style="width: 220px" show-password></el-input>
        </el-form-item>
        <el-form-item label="输入的密码" prop="pwdval">
          <span v-text="queryParams.pwdval"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="文本域 textarea" prop="textarea">
          <el-input type="textarea" :rows="2" placeholder="请输入内容" v-model="queryParams.textarea" style="width: 220px">
          </el-input>
        </el-form-item>
        <el-form-item label="输入的密码" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="下拉框 select" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="下拉多选 select" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="级联选择 Cascader" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="时间选择" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="日期选择" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      
      <el-row>
        <el-form-item label="日期时间选择" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="日期范围" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="颜色选择" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="复选框" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="单选按钮" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="穿梭框" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="上传" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="下载" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      
      <el-row>
        <el-form-item label="弹对话框" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="弹页签" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="消息框" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="通知框" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="下拉菜单" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="超链接" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="步骤条" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="进度条" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="时间线" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="计数器" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="开关" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="滑块" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="按钮" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="Icon 图标" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

      <el-row>
        <el-form-item label="Rate 评分" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      
      <el-row>
        <el-form-item label="树控件" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="描述列表" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="标记" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      
      <el-row>
        <el-form-item label="气泡确认框" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="Popover 弹出框" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="Carousel 走马灯" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="Tooltip 文字提示" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="卡片展示" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      <el-row>
        <el-form-item label="折叠面板" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      
      <el-row>
        <el-form-item label="Calender日历" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      
      <el-row>
        <el-form-item label="Result结果" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      
      <el-row>
        <el-form-item label="分页" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>
      
      <el-row>
        <el-form-item label="from 表单" prop="textarea">

        </el-form-item>
        <el-form-item label="带定义" prop="textarea">
          <span v-text="queryParams.textarea"></span>
        </el-form-item>
      </el-row>

    </el-form>
  </div>
</template>
<script>

export default {
  dicts: ["sys_yes_no"],
  name: "Test Demo",
  components: {},
  props: [],
  data() {
    return {
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      mediumList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        normalText: undefined,
        pwdval: undefined,
        textarea: undefined,

        dbVersion: undefined,
        versionType: undefined,
        hasPostgis: undefined,
        hasSignature: undefined,
        publishTime: undefined,
      },
      rules: {
        expiresTime: [],
        prodType: [{
          required: true,
          message: '请选择产品类型',
          trigger: 'change'
        }],
      },
    }
  },
  computed: {},
  watch: {},
  created() {},
  mounted() {},
  methods: {
    submitForm() {
      this.$refs['elForm'].validate(valid => {
        if (!valid) return
      })
    },
    resetForm() {
      this.$refs['elForm'].resetFields()
    },
  }
}
</script>
<style lang="scss" scoped>

</style>