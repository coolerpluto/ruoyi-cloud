<template>
  <div class="app-container" :style="{pointerEvents:oppdata.action=='V'?'none':'unset'}">
    <h3>{{ stageTitle }}</h3>
    <el-divider/>
    <el-form ref="competitorInfoForm" v-loading="flag.competitorLoading" :model="competitorInfoForm" size="medium" label-width="220px">
      <el-button style="float:right;" type="info" @click="openDialog" icon="el-icon-plus">添加竞争对手</el-button>
      <el-table :data="competitorInfoForm.competitorInfo">
        <el-table-column label="竞争对手" align="center" prop="competitorName" :show-overflow-tooltip="true"/>
        <el-table-column label="参与项目/产品名" align="center" prop="product" :show-overflow-tooltip="true"/>
        <el-table-column label="单价/报价" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">{{ scope.row.unitPrice }} / {{ scope.row.tenderPrice }}</template>
        </el-table-column>
        <el-table-column label="对方优势" align="center" prop="advantage" :show-overflow-tooltip="true"/>
        <el-table-column label="投标总价" align="center" prop="tenderTotalPrice" :show-overflow-tooltip="true"/>
        <el-table-column label="我方具备情况" align="center" prop="hasSameAdvantage" :show-overflow-tooltip="true"/>
        <el-table-column align="center" prop="memo" :show-overflow-tooltip="true">
          <template slot="header" slot-scope="scope">
            <label>备注<br><span style="color: red;">(销售人员对该竞争对手的其他描述)</span></label>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="editCompetitor(scope.row)" icon="el-icon-edit">修改</el-button>
            <el-button size="mini" type="text" @click="removeCompetitor(scope.row)" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div id="competitorVerify" align="center" v-if="flag.competitorVerify">最少有一个对手</div>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="competitorDialog.title" :visible.sync="competitorDialog.open" width="1000px" append-to-body>
      <el-form ref="competitorDialog.form" :model="competitorDialog.form" :rules="competitorDialog.rules" label-width="100px">
        <el-row>
          <el-col :span="8"></el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDialogForm">确 定</el-button>
        <el-button @click="cancelDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getOppCompetitorInfo } from "@/api/crm/oppUnitedInfo"
export default {
  name: "competitorInfo",
  props: {
    stageShow: {
      type: Number,
      default: 2
    },
    stageTitle: {
      type: String,
      default: '竞争对手'
    },
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    },
  },
  data() {
    return {
      competitorInfoForm: {
        competitorInfo:[]
      },
      competitorInfoModified:{},
      competitorInfoOriginBak:{},
      flag:{
        competitorLoading: false,
        competitorVerify:false,
      },
      //组件弹框承载
      competitorDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        rules: {},
      },
    }
  },
  created(){
    this.initCompetitorInfo()
  },
  methods:{
    initCompetitorInfo(){
      var _this = this
      //console.log("advancesInfoForm:",_this.oppdata)
      // 开启遮盖层
      this.flag.competitorLoading = true;
      this.getOppCompetitorInfo(function(){
        //console.log("competitorInfoForm:",_this.competitorInfoForm)
        _this.flag.competitorLoading = false;
      })
    },
    getOppCompetitorInfo(func){
      if(!this.oppdata.opportunityCode||this.oppdata.opportunityCode === '0'){
        if (typeof func == 'function'){
          func();
        }
        return
      }      
      getOppCompetitorInfo({code:this.oppdata.opportunityCode}).then(response => {
        if (response.code !== 200){
          this.$modal.msgError(response.msg);
          return
        }
        this.competitorInfoForm.competitorInfo = response.data;
        
        this.competitorInfoOriginBak = JSON.parse(JSON.stringify(this.competitorInfoForm))

        if (typeof func == 'function'){
          func();
        }
      })
    },
    
    removeCompetitor(row) {

    },
    editCompetitor(row) {
      this.competitorDialog.open = true;
      this.competitorDialog.title = "修改竞争对手";
    },
    openDialog() {
      this.competitorDialog.open = true;
      this.competitorDialog.title = "添加新竞争对手";
    },
    submitDialogForm() {
      this.competitorDialog.open = false;
    },
    cancelDialog() {
      this.competitorDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      // 暂时不校验必填 无敌是多么寂寞
      // if (!this.competitorInfoForm.competitorInfo || this.competitorInfoForm.competitorInfo.length == 0) {
      //   this.flag.competitorVerify = true
      //   flag = false
      // }
      this.$refs["competitorInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {      
      return {
              currentData:this.competitorInfoForm,//最新展示数据
              modifyedData:this.competitorInfoModified,//改动部分
              originData:this.competitorInfoOriginBak,//原始数据
            };
    },
  },
}
</script>

<style scoped>
#competitorVerify{
  color: #ff4949;
    font-size: 12px;
    line-height: 3;
}
</style>
