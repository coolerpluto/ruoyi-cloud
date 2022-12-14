<template>
  <!-- 新建表 -->
  <el-dialog title="新建表(录入基本信息,其他信息进去编辑中填写)" :visible.sync="visible" width="1000px" top="5vh" append-to-body>
    <el-form :model="info" ref="fromParam" size="small" :inline="true" :rules="rules"  label-width="140px">
      <el-row>
        <el-col :span="12">
          <el-form-item label="表名称" prop="tableName">
            <el-input placeholder="请输入仓库名称" v-model="info.tableName"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="表描述" prop="tableComment">
            <el-input placeholder="请输入" v-model="info.tableComment"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="实体类名称" prop="className">
            <el-input placeholder="请输入" v-model="info.className"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="作者" prop="functionAuthor">
            <el-input placeholder="请输入" v-model="info.functionAuthor"/>
          </el-form-item>
        </el-col>
        <el-col :span="24">
          <el-form-item label="备注" prop="remark">
            <el-input type="textarea" :rows="3" v-model="info.remark"></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button type="primary" @click="handleAddTable">确 定</el-button>
      <el-button @click="visible = false">取 消</el-button>
    </div>
  </el-dialog>
</template>

<script>
import {addTable} from "@/api/tool/gen";
export default {
  data() {
    return {
      // 遮罩层
      visible: false,
      // 参数
      info: {
        tableName: undefined,
        tableComment: undefined,
        className: undefined,
        functionAuthor: undefined,
        remark: undefined,
      },
      rules: {
        tableName: [
          { required: true, message: "后期可以修改,这里必填", trigger: "blur" }
        ],
      }
    };
  },
  methods: {
    // 显示弹框
    show() {
      this.visible = true;
      this.reset();
    },
    // 表单重置
    reset() {
      this.info = {
        tableName: undefined,
        tableComment: undefined,
        className: undefined,
        functionAuthor: undefined,
        remark: undefined,
      };
      this.resetForm("fromParam");
    },
    /** 导入按钮操作 */
    handleAddTable() {
      this.$refs["fromParam"].validate((valid) => {
        if (!valid) {
          return;
        }
        addTable(this.info).then(res => {
          this.$modal.msgSuccess(res.msg);
          if (res.code === 200) {
            this.visible = false;
            this.$emit("ok");
          }
        });
      });
    },
  }
};
</script>
