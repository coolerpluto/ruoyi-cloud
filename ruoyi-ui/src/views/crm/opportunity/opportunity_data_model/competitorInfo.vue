<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>{{ stageTitle }}</h3>
    <el-divider />
    <el-form ref="competitorInfoForm" v-loading="flag.competitorLoading" :model="competitorInfoForm" size="medium"
      label-width="220px">
      <el-button style="float:right;" type="info" @click="openDialog" icon="el-icon-plus">添加竞争对手</el-button>
      <el-table :data="competitorInfoForm.competitorInfo">
        <el-table-column label="竞争对手" align="center" prop="competitorName" :show-overflow-tooltip="true" />
        <el-table-column label="参与项目/产品名" align="center" prop="product" :show-overflow-tooltip="true" />
        <el-table-column label="单价/报价" align="center" :show-overflow-tooltip="true">
          <template slot-scope="scope">{{ scope.row.unitPrice }} / {{ scope.row.tenderPrice }}</template>
        </el-table-column>
        <el-table-column label="投标总价" align="center" prop="tenderTotalPrice" :show-overflow-tooltip="true" />
        <el-table-column label="对方优势" align="center" prop="advantage" :show-overflow-tooltip="true" />
        <el-table-column label="我方是否具备" align="center" prop="hasSameAdvantage" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.sys_yes_no" :value="scope.row.hasSameAdvantage" />
          </template>
        </el-table-column>
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
      <el-form ref="competitorDialog.form" :model="competitorDialog.form" :rules="competitorDialog.rules"
        label-width="120px">
        <el-row>
          <el-col :span="11">
            <el-form-item label="竞争对手" prop="competitorName">
              <el-input v-model="competitorDialog.form.competitorName" placeholder="请输入或选择竞争对手">
                <el-button slot="append" icon="el-icon-search" @click="openCompanyDialog">选择</el-button>
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="项目/产品" prop="product">
              <el-input v-model="competitorDialog.form.product" placeholder="请输入参与项目或者产品名称" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="单价(卖价)" prop="unitPrice">
              <el-input v-model="competitorDialog.form.unitPrice" placeholder="请输入对手产品的卖价" />
              {{ convertCurrencyDX(competitorDialog.form.unitPrice || 0) }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="投标单价" prop="tenderPrice">
              <el-input v-model="competitorDialog.form.tenderPrice" placeholder="请输入对手的投标单价" />
              {{ convertCurrencyDX(competitorDialog.form.tenderPrice || 0) }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="投标总价" prop="tenderTotalPrice">
              <el-input v-model="competitorDialog.form.tenderTotalPrice" placeholder="请输入对手的投标总价" />
              {{ convertCurrencyDX(competitorDialog.form.tenderTotalPrice || 0) }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="对方优势" prop="advantage">
              <el-select v-model="competitorDialog.form.advantage" placeholder="选择对方优势">
                <el-option v-for="item in dict.type.crm_oppo_other_side_advantage" :key="item.value" :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="我方是否具备" prop="hasSameAdvantage">
              <el-radio-group v-model="competitorDialog.form.hasSameAdvantage">
                <el-radio v-for="item in dict.type.sys_yes_no" :key="item.value" :label="item.value">{{ item.label }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="memo">
              <el-input v-model="competitorDialog.form.memo" type="textarea" resize="none" :rows="2"
                placeholder="对竞争对手的其他描述">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitDialogForm">确 定</el-button>
        <el-button @click="cancelDialog">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog :title="companyDialog.title" :visible.sync="companyDialog.open" :show-close="false" label-width="150px"
      width="800px" append-to-body>
      <el-form ref="companyDialog.form" :model="companyDialog.form" @submit.native.prevent>
        <el-row>
          <el-col :span="15">
            <el-input v-model="companyDialog.form.companyName" placeholder="请输入公司名称关键字" clearable />
          </el-col>
          <el-col :span="5">
            <el-button type="primary" icon="el-icon-search" size="mini" @click="getListCompanys">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="companyDialog.form.companyName = ''">重置</el-button>
          </el-col>
        </el-row>
      </el-form>
      <el-table v-loading="flag.dialogCompanyTableLoading" highlight-current-row @current-change="handleCurrentChange"
        :data="companyDialog.companys">
        <el-table-column label="公司名称" align="center" prop="companyName" :show-overflow-tooltip="true" />
        <el-table-column label="公司性质" align="center" prop="properties" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_company_properties_type" :value="scope.row.properties" />
          </template>
        </el-table-column>
        <el-table-column label="公司法人" align="center" prop="legal" :show-overflow-tooltip="true" />
        <el-table-column label="公司地址" align="center" prop="addrDetail" :show-overflow-tooltip="true" />
      </el-table>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitCompanyDialogForm">确 定</el-button>
        <el-button @click="cancelCompanyDialog">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getOppCompetitorInfo } from "@/api/crm/oppUnitedInfo";
import { listCompany } from "@/api/crm/company";

export default {
  name: "competitorInfo",
  dicts: ['sys_yes_no', 'crm_company_properties_type','crm_oppo_other_side_advantage'],
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
        competitorInfo: []
      },
      competitorInfoModified: {},
      competitorInfoOriginBak: {},
      flag: {
        competitorLoading: false,
        competitorVerify: false,
        dialogCompanyTableLoading: false,
      },
      //组件弹框承载
      competitorDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        rules: {
          competitorName: [
            { required: true, message: "竞争对手名称不能为空", trigger: "blur" }
          ],
          product: [
            { required: true, message: "项目/产品名称不能为空", trigger: "blur" }
          ],
          unitPrice: [
            { pattern: /^[+-]?(0|([1-9]\d*))(\.\d{1,2})?$/, message: "请输入正确金额,最多两位小数", trigger: "blur" },
            { required: true, message: "单价(卖价)不能为空", trigger: "blur" }
          ],
          tenderPrice: [
            { pattern: /^[+-]?(0|([1-9]\d*))(\.\d{1,2})?$/, message: "请输入正确金额,最多两位小数", trigger: "blur" },
            { required: true, message: "投标单价不能为空", trigger: "blur" }
          ],
          tenderTotalPrice: [
            { pattern: /^[+-]?(0|([1-9]\d*))(\.\d{1,2})?$/, message: "请输入正确金额,最多两位小数", trigger: "blur" },
            { required: true, message: "投标总价不能为空", trigger: "blur" }
          ],
          advantage: [
            { required: true, message: "对方优势未填写", trigger: "blur" }
          ],
          hasSameAdvantage: [
            { required: true, message: "我方是否具备未选择", trigger: "blur" }
          ],
          memo: [
            { required: true, message: "备注对竞争对手的其他描述不能为空", trigger: "blur" }
          ],
        },
      },
      companyDialog: {
        // 弹出层标题
        title: "公司信息查找",
        // 是否显示弹出层
        open: false,
        form: {
          pageNum: 1,
          pageSize: 15,
          //sourceType: "customer",
          //businessScope:"S", // 公司业务中带S的
          companyName: ""
        },
        selectedCompany: {},
        companys: []
      },
    }
  },
  created() {
    this.initCompetitorInfo()
  },
  methods: {
    initCompetitorInfo() {
      var _this = this
      // 开启遮盖层
      this.flag.competitorLoading = true;
      this.getOppCompetitorInfo(function () {
        //console.log("competitorInfoForm:",_this.competitorInfoForm)
        _this.flag.competitorLoading = false;
      })
    },
    getOppCompetitorInfo(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppCompetitorInfo({ code: this.oppdata.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.competitorInfoForm.competitorInfo = response.data;
        this.competitorInfoOriginBak = JSON.parse(JSON.stringify(this.competitorInfoForm))
        if (typeof func == 'function') {
          func();
        }
      })
    },
    removeCompetitor(row) {
      let index = this.competitorInfoForm.competitorInfo.findIndex(item => {
        if (row.tempId) {
          return item.tempId == row.tempId//新建的或者点击过修改
        } else {
          return item.id == row.id ////初始化来的还未点击过
        }
      })
      this.competitorInfoForm.competitorInfo.splice(index, 1);
    },
    editCompetitor(row) {
      this.competitorDialog.form = Object.assign({}, row);
      this.competitorDialog.open = true;
      this.competitorDialog.title = "修改竞争对手";
    },
    openDialog() {
      this.competitorDialog.open = true;
      this.competitorDialog.form = {}
      this.competitorDialog.title = "添加新竞争对手";
    },
    openCompanyDialog() {
      this.companyDialog.open = true;
    },
    getListCompanys() {
      this.flag.dialogCompanyTableLoading = true
      listCompany(this.companyDialog.form).then(response => {
        this.companyDialog.companys = (response.rows);
        this.companyDialog.total = response.total;
        this.flag.dialogCompanyTableLoading = false;
      });
    },
    handleCurrentChange(val) {
      this.companyDialog.selectedCompany = val
    },
    cancelCompanyDialog() {
      this.companyDialog.open = false;
    },
    submitCompanyDialogForm() {
      if (Object.keys(this.companyDialog.selectedCompany).length == 0) {
        this.$modal.msgError("您还未选择公司信息");
        return;
      }
      this.competitorDialog.form.competitorName = this.companyDialog.selectedCompany.companyName;
      this.competitorDialog.form.competitorId = this.companyDialog.selectedCompany.id;
      this.companyDialog.open = false;
    },
    submitDialogForm() {
      this.$refs["competitorDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        if (!this.competitorDialog.form.id && !this.competitorDialog.form.tempId) {
          this.competitorDialog.form.tempId = new Date().getTime()//先打个标
          this.competitorInfoForm.competitorInfo.push(this.competitorDialog.form)
        }
        // 替换
        this.competitorInfoForm.competitorInfo.map((item, i) => {
          //带来的
          if (item.id && item.id == this.competitorDialog.form.id) {
            this.competitorInfoForm.competitorInfo.splice(i, 1, this.competitorDialog.form)
          }
          //新增的
          if (item.tempId && item.tempId == this.competitorDialog.form.tempId) {
            this.competitorInfoForm.competitorInfo.splice(i, 1, this.competitorDialog.form)
          }
        })
        this.competitorDialog.open = false;
      });
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
    //提取修改的信息作为提交
    fetchInformation() {
      if (Object.keys(this.competitorInfoOriginBak).length == 0) {
        this.competitorInfoModified.competitorInfo_a = this.competitorInfoForm.competitorInfo
        return;
      }
      this.competitorInfoModified.competitorInfo_a = []
      this.competitorInfoModified.competitorInfo_m = []
      this.competitorInfoModified.competitorInfo_d = []
      for (let options of this.competitorInfoForm.competitorInfo) {
        if (!options.id) {
          this.competitorInfoModified.competitorInfo_a.push(options)
          continue;
        }
        let currentOld = this.competitorInfoOriginBak.competitorInfo.find(item => { return item.id == options.id; })
        if (this.objEqual(currentOld, options)) {
          continue;
        }
        this.competitorInfoModified.competitorInfo_m.push(options)
      }
      for (let options of this.competitorInfoOriginBak.competitorInfo) {
        let current = this.competitorInfoForm.competitorInfo.find(item => { return item.id == options.id; })
        if (current) {
          continue;
        }
        this.competitorInfoModified.competitorInfo_d.push(options)
      }
    },
    //对象属性全部平铺的情况
    objEqual(oldObj, newObj) {
      for (let key in oldObj) {
        // 不考虑new新加的标识属性
        if (oldObj[key] !== newObj[key]) {
          return false;
        }
      }
      return true;
    },
    // 提供本组件的全部数据
    collectInfo() {
      this.fetchInformation();
      return {
        currentData: this.competitorInfoForm,//最新展示数据
        modifyedData: this.competitorInfoModified,//改动部分
        originData: this.competitorInfoOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped>
#competitorVerify {
  color: #ff4949;
  font-size: 12px;
  line-height: 3;
}
</style>
