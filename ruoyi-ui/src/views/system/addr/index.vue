<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="地址名字" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入地址名字"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="地址级别" prop="level">
        <el-select v-model="queryParams.level" placeholder="请选择地址级别" clearable>
          <el-option
            v-for="item in addLevelOptions"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="地址状态" prop="status">
        <el-select v-model="queryParams.status" placeholder="请选择地址记录状态" clearable>
          <el-option
            v-for="dict in dict.type.sys_normal_disable"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-hasPermi="['system:addr:add']"
        >新增
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-if="refreshTable"
      v-loading="loading"
      :data="addrList"
      row-key="code"
      lazy
      :load="loadChild"
      :default-expand-all="isExpandAll"
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column label="唯一code" prop="code"/>
      <el-table-column label="地址名字" align="center" prop="name"/>
      <el-table-column label="地址级别" align="center" prop="level">
        <template slot-scope="{ row }">
           <span v-for="(item, index) in addLevelOptions" :key="index">
             {{ row.level == item.value? item.label: "" }}
           </span>
        </template>
      </el-table-column>
      <el-table-column label="使用状态" align="center" prop="status">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_normal_disable" :value="scope.row.status"/>
        </template>
      </el-table-column>
      <el-table-column label="同级排序" align="center" prop="sort"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:addr:edit']"
          >修改
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:addr:add']"
          >新增
          </el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:addr:remove']"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <!-- 添加或修改地址联动对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="地址名字" prop="name">
          <el-input v-model="form.name" placeholder="请输入地址名字"/>
        </el-form-item>
        <el-form-item label="唯一标识" prop="code">
          <el-input v-model="form.code" placeholder="请输入代号，不能重复"/>
        </el-form-item>
        <el-form-item label="上级地址" prop="parent">
          <treeselect v-model="form.parent" :options="addrOptions" :normalizer="normalizer" placeholder="请选择父级地址code"/>
        </el-form-item>
        <el-form-item label="地址级别" prop="level">
          <el-select v-model="form.level" placeholder="请选择地址级别">
            <el-option
              v-for="item in addLevelOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="地址状态" prop="status">
          <el-select v-model="form.status" placeholder="请选择地址记录状态">
            <el-option
              v-for="dict in dict.type.sys_normal_disable"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            ></el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {listAddr,listPageAddr, getAddr, delAddr, addAddr, updateAddr} from "@/api/system/addr";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Addr",
  dicts: ['sys_normal_disable'],
  components: {
    Treeselect
  },
  data() {
    return {
      // 遮罩层
      loading: false,
      // 显示搜索条件
      showSearch: true,
      // 地址联动表格数据
      addrList: [],
      // 地址联动表格原始数据
      addrListCopy: [],
      // 地址联动树选项
      addrOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否展开，默认全部展开
      isExpandAll: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: null,
        parent: null,
        //level: 'province',
        level: null,
        status: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {},
      addLevelOptions: [{
        value: "province",
        label: "省级"
      }, {
        value: "city",
        label: "市级"
      }, {
        value: "area",
        label: "县/区级"
      }, {
        value: "street",
        label: "镇/街级"
      }],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询地址联动列表 */
    getList() {
      this.loading = true;
      listAddr(this.queryParams).then(response => {
        //this.addrList = this.handleTree(response.data, "code", "parent");
        this.addrListCopy = this.handleTree(response.data, "code", "parent");
        console.log(this.addrListCopy);
        for(let i =0; i<this.addrListCopy.length; i++){
          this.addrList.push({
            id:this.addrListCopy[i].id,
            code:this.addrListCopy[i].code,
            name:this.addrListCopy[i].name,
            level:this.addrListCopy[i].level,
            status:this.addrListCopy[i].status,
            sort:this.addrListCopy[i].sort,
            children:null,
            hasChildren:this.addrListCopy[i].children && this.addrListCopy[i].children.length>0,
          })
        }
        this.loading = false;
      });
      this.getTreeselect();
    },
    loadChild(tree, treeNode, resolve){
      // 层级关系备份
      tree.idList = tree.idList||[tree.code]
      const idCopy = JSON.parse(JSON.stringify(tree.idList))
      // 查找下一层数据
      let resolveArr = this.addrListCopy
      let code =tree.code
      while (code = tree.idList.shift()) {
        const tarItem = resolveArr.find(item => item.code === code)
        //tarItem.loadedChildren = true
        //resolveArr = tarItem.children
        const tempArr = [];
        for(let i =0; i< tarItem.children.length; i++){
          tempArr.push({
            id:tarItem.children[i].id,
            code:tarItem.children[i].code,
            name:tarItem.children[i].name,
            level:tarItem.children[i].level,
            status:tarItem.children[i].status,
            sort:tarItem.children[i].sort,
            children:tarItem.children[i].children,
            hasChildren:tarItem.children[i].children && tarItem.children[i].children.length>0,
          })
        }
        resolveArr = tempArr
      }
      // 处理下一层数据的属性
      resolveArr = JSON.parse(JSON.stringify(resolveArr))
      resolveArr.forEach(item => {
        item.hasChildren = item.children && item.children.length > 0
        item.children = null
        // 此处需要深拷贝，以防各个item的idList混乱
        item.idList = JSON.parse(JSON.stringify(idCopy))
        item.idList.push(item.code)
      })
      // 标识已经加载子节点
      //tree.loadedChildren = true
      // 渲染子节点
      resolve(resolveArr)
    },
    /** 转换地址联动数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.code,
        label: node.name,
        children: node.children
      };
    },
    /** 查询地址联动下拉树结构 */
    async getTreeselect() {
      listAddr().then(response => {
        this.addrOptions = [];
        const data = {code: 0, name: '中国', children: []};
        data.children = this.handleTree(response.data, "code", "parent");
        this.addrOptions.push(data);
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        code: null,
        name: null,
        parent: null,
        level: null,
        status: null,
        sort: null,
        lat: null,
        lng: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      if (row != null && row.code) {
        this.form.parent = row.code;
      } else {
        this.form.parent = 0;
      }
      this.open = true;
      this.title = "添加地址";
    },
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.refreshTable = false;
      this.isExpandAll = !this.isExpandAll;
      this.$nextTick(() => {
        this.refreshTable = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      if (row != null) {
        this.form.parent = row.code;
      }
      this.open = true;
      getAddr(row.id).then(response => {
        this.form = response.data;
        this.title = "修改地址";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.id != null) {
            updateAddr(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAddr(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除地址联动编号为"' + row.id + '"的数据项？').then(function () {
        return delAddr(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    }
  }
};
</script>
