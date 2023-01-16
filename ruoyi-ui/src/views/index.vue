<template>
  <div class="app-container home">
    <el-row :gutter="20">
      <el-col :sm="24" style="padding-left: 20px">
        <div style="width: 100%;text-align: center;" >
          <span style="font-size: xx-large;color: darkorchid;" >欢迎进入统一管理系统</span>
        </div>
        <div style="width: 100%;">
          <p style="float: left;">
            <b>当前版本:</b> <span>v{{ version }}</span>
          </p>
          <p style="float: right;">
            <b>当前时间:</b> <span>{{ dateFormat(currentTime) }}</span>
          </p>
        </div>
      </el-col>
    </el-row>
    <el-divider/>
    <el-row :gutter="20">
      <el-col :sm="24" :lg="19" style="padding-left: 20px">
        <h2>
          通知公告
          <right-toolbar @queryTable="getNoticeList"></right-toolbar>
        </h2>
        <el-collapse accordion>
          <el-collapse-item v-for="(notice,index) in noticeList" :name="index">
            <template slot="title">
              <div style="width: 100%;">
                <p style="float: left;"> 主题：{{ notice.noticeTitle }}</p>
                <p align="right"> 发布时间：{{ notice.createTime }}</p>
              </div>
            </template>
            <div v-html="notice.noticeContent"></div>
          </el-collapse-item>
        </el-collapse>
      </el-col>

      <el-col :sm="24" :lg="5" style="padding-left: 20px">
        <h2>技术支持</h2>
        <el-collapse accordion>
          <el-collapse-item v-for="(dict,index) in dict.type.sys_user_standby" :name="index">
            <template slot="title">
              {{ dict.label }}--EM 账户：{{ dict.value }}
            </template>
            <div> {{ dict.raw.remark }}</div>
          </el-collapse-item>
        </el-collapse>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {listNotice} from "@/api/system/notice";

export default {
  name: "Index",
  dicts: ['sys_user_standby'],
  data() {
    return {
      // 版本号
      version: "3.6.2",
      currentTime: new Date(),
      activeName: '1',
      // 公告表格数据
      noticeList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 6,
        status: 0
      },
    };
  },
  // 挂载时间
  mounted() {
    let that = this
    this.timer = setInterval(function () {
      that.currentTime = new Date().toLocaleString()
    })
  },
  // 销毁时清除计时器
  beforeDestroy: function () {
    if (this.timer) {
      clearInterval(this.timer)
    }
  },
  created() {
    this.getNoticeList();
  },
  methods: {
    goTarget(href) {
      window.open(href, "_blank");
    },
    /** 查询公告列表 */
    getNoticeList() {
      listNotice(this.queryParams).then(response => {
        this.noticeList = response.rows;
        this.total = response.total;
      });
    },
    // 时间格式化
    dateFormat() {
      var date = new Date()
      var year = date.getFullYear()
      // 在日期格式中，月份是从0开始的
      // 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
      var month = date.getMonth() + 1 < 10 ? '0' + (date.getMonth() + 1) : date.getMonth() + 1
      var day = date.getDate() < 10 ? '0' + date.getDate() : date.getDate()
      var hours = date.getHours() < 10 ? '0' + date.getHours() : date.getHours()
      var minutes = date.getMinutes() < 10 ? '0' + date.getMinutes() : date.getMinutes()
      var seconds = date.getSeconds() < 10 ? '0' + date.getSeconds() : date.getSeconds()
      let week = date.getDay() // 星期
      let weekArr = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六']
      // 拼接 时间格式处理
      return year + '年' + month + '月' + day + '日 ' + hours + ':' + minutes + ':' + seconds + ' ' + weekArr[week]
    },
  },
};
</script>

<style scoped lang="scss">
.home {
  blockquote {
    padding: 10px 20px;
    margin: 0 0 20px;
    font-size: 17.5px;
    border-left: 5px solid #eee;
  }

  hr {
    margin-top: 20px;
    margin-bottom: 20px;
    border: 0;
    border-top: 1px solid #eee;
  }

  .col-item {
    margin-bottom: 20px;
  }

  ul {
    padding: 0;
    margin: 0;
  }

  font-family: "open sans", "Helvetica Neue", Helvetica, Arial, sans-serif;
  font-size: 13px;
  color: #676a6c;
  overflow-x: hidden;

  ul {
    list-style-type: none;
  }

  h4 {
    margin-top: 0px;
  }

  h2 {
    margin-top: 10px;
    font-size: 26px;
    font-weight: 100;
  }

  p {
    margin-top: 10px;

    b {
      font-weight: 700;
    }
  }

  .update-log {
    ol {
      display: block;
      list-style-type: decimal;
      margin-block-start: 1em;
      margin-block-end: 1em;
      margin-inline-start: 0;
      margin-inline-end: 0;
      padding-inline-start: 40px;
    }
  }
}
</style>

