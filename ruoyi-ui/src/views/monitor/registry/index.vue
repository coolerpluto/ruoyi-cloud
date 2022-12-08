<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">刷新</el-button>
      </el-form-item>
      <el-form-item>
        <el-link type="primary">
          默认链接:http://192.168.110.204:5000/v2/_catalog 若修改请至nacos</el-link>
      </el-form-item>
    </el-form>
    <el-table v-loading="loading" :data="list" style="width: 100%">
      <el-table-column label="序号" type="index" width="50" />
      <el-table-column label="镜像名字" align="center" prop="registryName" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-search" @click="getTags(scope.row.registryName)"
            v-hasPermi="['monitor:online:forceLogout']">查看 Tags</el-button>
        </template>
      </el-table-column>
    </el-table>

    <!-- 对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-table :data="tagList" style="width: 100%">
        <el-table-column label="序号" type="index" width="50" />
        <el-table-column label="Tag 版本" align="center" prop="tag" :show-overflow-tooltip="true"/>
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listCatalog, listTags } from "@/api/monitor/registry";

export default {
  name: "Registry",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      tagList: [],
      // 是否显示弹出层
      open: false,
      // 弹出层标题
      title: "",
      // 查询参数
      queryParams: {
        ipaddr: undefined,
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      listCatalog().then((response) => {
        this.list = [];
        for (var key in response.repositories) {
          this.list.push({ registryName: response.repositories[key] });
        }
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    getTags(image) {
      this.loading = true;
      listTags(image).then((response) => {
        this.tagList = [];
        for (var key in response.tags) {
          this.tagList.push({ tag: response.tags[key] });
        }
        this.title = "镜像名：" + response.name;
        this.open = true;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.loading = false;
      this.getList();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    }
  }
};
</script>

