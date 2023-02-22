<template>
  <div class="app-container">
    <h3>基础信息</h3>
    <el-divider/>
    <el-form ref="baseInfoForm" :model="baseInfoForm" size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-col :span="12">
          <el-form-item label="商机名称" prop="name">
            <el-input v-model="baseInfoForm.name" placeholder="请输入商机名称" clearable :style="{width: '100%'}">
            </el-input>
          </el-form-item>
        </el-col>
        <el-col :span="12">
          <el-form-item label="商机编码" prop="code">
            <label placeholder="自动生产商机编码">{{baseInfoForm.code}}</label>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row :gutter="15">
        <el-col :span="24">
          <el-form-item label="应用运营信息">
            <el-button style="float:right;" type="info" icon="el-icon-plus">添加新条目</el-button>
            <el-table :data="baseInfoForm.appOperationInfo">
              <el-table-column label="客户系统名称" align="center" fixed prop="id" :show-overflow-tooltip="true"/>
              <el-table-column label="系统开发商" align="center" fixed prop="id" :show-overflow-tooltip="true"/>
              <el-table-column label="适配进度" align="center" fixed prop="id" :show-overflow-tooltip="true"/>
              <el-table-column label="招标前必须完成度" align="center" fixed prop="id" :show-overflow-tooltip="true"/>
              <el-table-column label="软件分类" align="center" fixed prop="id" :show-overflow-tooltip="true"/>
              <el-table-column label="项目运作主体" align="center" fixed prop="id" :show-overflow-tooltip="true"/>
              <el-table-column label="操作" align="center" fixed prop="id" :show-overflow-tooltip="true">
                <template slot-scope="scope">
                  <el-button size="mini" type="text" icon="el-icon-edit">修改</el-button>
                  <el-button size="mini" type="text" icon="el-icon-delete">删除</el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

  </div>
</template>

<script>
import appOperationInfo from "./appOperationInfo";
export default {
  name: "baseInfo",
  components: {},
  data() {
    return {
      baseInfoForm: {},
    };
  }
}
</script>

<style scoped>

</style>
