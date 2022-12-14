<template>
  <!-- 新建表列 -->
  <el-dialog title="新建表字段(录入基本信息,其他信息在编辑中填写)" :visible.sync="visible" width="1000px" top="5vh" append-to-body>
    <el-form :model="info" ref="fromParam" size="small" :inline="true" :rules="rules"  label-width="140px">
      <el-row>
        <el-col :span="12">
          <el-form-item prop="columnName">
            <span slot="label">
              字段列名
              <el-tooltip content="数据库列名，不可二次修改" placement="top">
                <i class="el-icon-question"></i>
              </el-tooltip>
            </span>
            <el-input placeholder="请输入列名" v-model="info.columnName"/>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item prop="columnType">
            <span slot="label">
              字段类型
              <el-tooltip content="数据库列对应的数据库类型 例如 varchar(100)，不可二次修改" placement="top">
                <i class="el-icon-question"></i>
              </el-tooltip>
            </span>
            <template slot-scope="scope">
              <el-select v-model="info.columnType">
                <el-option label="char" value="char" />
                <el-option label="varchar" value="varchar" />
                <el-option label="text" value="text" />
                <el-option label="int" value="int" />
                <el-option label="bigint" value="bigint" />
                <el-option label="FLOAT" value="FLOAT" />
                <el-option label="DOUBLE" value="DOUBLE" />
                <el-option label="date" value="date" />
                <el-option label="time" value="time" />
                <el-option label="datetime" value="datetime" />
                <el-option label="TIMESTAMP" value="TIMESTAMP" />
              </el-select>
            </template>
          </el-form-item>
        </el-col>
        <el-col :span="12" v-if="1==2">
          <el-form-item label="表的ID" prop="tableId">
            <el-input placeholder="请输入" v-model="tableInfo.tableId"/>
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
import {addTableColumn} from "@/api/tool/gen";
export default {
  props: {
    tableInfo: {
      type: Object,
      default: null
    }
  },
  data() {
    return {
      // 遮罩层
      visible: false,
      // 参数
      info: {
        columnName: undefined,
        columnType: undefined,
        tableId:this.tableInfo.tableId,
      },
      rules: {
        columnName: [
          { required: true, message: "后期不可以修改,这里慎填", trigger: "blur" }
        ],
        columnType: [
          { required: true, message: "后期不可以修改,这里慎填", trigger: "blur" }
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
        columnName: undefined,
        columnType: undefined,
        tableId:this.tableInfo.tableId,
      };
      this.resetForm("fromParam");
    },
    /** 导入按钮操作 */
    handleAddTable() {
      this.$refs["fromParam"].validate((valid) => {
        if (!valid) {
          return;
        }
        addTableColumn(this.info).then(res => {
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
