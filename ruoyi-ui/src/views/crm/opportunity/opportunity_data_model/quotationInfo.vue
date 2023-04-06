<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>产品以及报价</h3><el-divider />
    <el-form ref="quotationInfoForm" v-loading="flag.quotationLoading" :model="quotationInfoForm" size="medium"
      label-width="220px">
      <el-button style="float:right;" type="info" @click="openDialog" icon="el-icon-plus">添加报价</el-button>
      <el-table :data="quotationInfoForm.quotationInfo">
        <el-table-column label="产品名称" align="center" prop="quotationMap.productName" :show-overflow-tooltip="true" />
        <el-table-column label="版本" align="center" prop="quotationMap.productVersion" :show-overflow-tooltip="true" />
        <el-table-column label="版本号" align="center" prop="quotationMap.productSecVersion" :show-overflow-tooltip="true" />
        <el-table-column label="数量" align="center" prop="quotationMap.productCount" :show-overflow-tooltip="true" />
        <el-table-column label="单价(元)" align="center" prop="quotationMap.productPrice" :show-overflow-tooltip="true" />
        <el-table-column label="总价(元)" align="center" prop="quotationMap.productTotalPrice"
          :show-overflow-tooltip="true" />
        <el-table-column label="报价阶段" align="center" prop="quotationMap.quotationStage" :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.crm_opportunity_status" :value="scope.row.quotationMap.quotationStage" />
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button size="mini" type="text" @click="editQuotation(scope.row)" icon="el-icon-edit">修改</el-button>
            <el-button size="mini" type="text" @click="removeQuotation(scope.row)" icon="el-icon-delete">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <div id="quotationVerify" align="center" v-if="flag.quotationVerify">当前阶段最少有一条报价</div>
    </el-form>
    <!-- 添加或修改信息记录对话框 -->
    <el-dialog :title="quotationDialog.title" :visible.sync="quotationDialog.open" width="600px" append-to-body>
      <el-form ref="quotationDialog.form" :model="quotationDialog.form" :rules="quotationDialog.rules"
        label-width="100px">
        <el-row>
          <el-col :span="11">
            <el-form-item label="产品名称" prop="productCode">
              <el-select v-model="quotationDialog.form.productCode" @change="selectProducted">
                <el-option v-for="item in dict.type.crm_opportunity_main_product" :key="item.value" :label="item.label"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="11" v-if="false">
            <el-form-item label="产品名称1" prop="productName">
              <el-input v-model="quotationDialog.form.productName">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="报价阶段" prop="quotationStage">
              <el-select v-model="quotationDialog.form.quotationStage" disabled>
                <el-option v-for="item in dict.type.crm_opportunity_status" :key="item.value" :label="item.label"
                  :value="item.value"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="版本" prop="productVersion">
              <el-select v-model="quotationDialog.form.productVersion" @change="selectProductVersion">
                <el-option v-for="item in quotationDialog.productVersion" 
                  :key="item.dictValue" :label="item.dictLabel"
                  :value="item.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="版本号" prop="productSecVersion">
              <el-select v-model="quotationDialog.form.productSecVersion">
                <el-option v-for="item in quotationDialog.productSecVersion" :key="item.dictValue" :label="item.dictLabel"
                  :value="item.dictValue"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="数量" prop="productCount">
              <el-input v-model="quotationDialog.form.productCount" @change="calculation" placeholder="请输入报价数量"
                type="number">
              </el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="单价" prop="productPrice">
              <el-input v-model="quotationDialog.form.productPrice" @change="calculation" placeholder="请输入报价单价"
                type="number">
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="总价" prop="productTotalPrice">
              <el-input v-model="quotationDialog.form.productTotalPrice" readonly type="number">
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
  </div>
</template>

<script>
import { getOppQuotationInfo } from "@/api/crm/oppUnitedInfo"
export default {
  name: "quotationInfo",
  dicts: ['crm_opportunity_status', 'crm_opportunity_main_product'],
  props: {
    stageShow: {
      type: Number,
      required: true
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
      quotationInfoForm: { quotationInfo: [] },
      quotationInfoModified: {},
      quotationInfoOriginBak: {},
      flag: {
        quotationLoading: false,
        quotationVerify: false,
      },
      //组件弹框承载
      quotationDialog: {
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        form: {},
        currentEdit: {},
        rules: {
          productCode: [
            { required: true, message: "未选择报价产品", trigger: "blur" }
          ],
          productVersion: [
            { required: true, message: "未选择报价版本", trigger: "blur" }
          ],
          productSecVersion: [
            { required: true, message: "未选择报价版本号", trigger: "blur" }
          ],
          productCount: [
            { required: true, message: "未填写报价数量", trigger: "blur" }
          ],
          productPrice: [
            { required: true, message: "未填写报价单价", trigger: "blur" }
          ],
        },
        productVersion: [],
        productSecVersion: [],
      },
    }
  },
  created() {
    this.initQuotationInfo()
  },
  methods: {
    initQuotationInfo() {
      var _this = this
      // 开启遮盖层
      this.flag.quotationLoading = true;
      this.getOppQuotationInfo(function () {
        _this.flag.quotationLoading = false;
      })
    },
    getOppQuotationInfo(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      getOppQuotationInfo({ code: this.oppdata.opportunityCode }).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.quotationInfoForm.quotationInfo = response.data;
        this.quotationInfoForm.quotationInfo.forEach(e => {
          e.quotationMap = JSON.parse(e.quotationJson);
        })
        this.quotationInfoOriginBak = JSON.parse(JSON.stringify(this.quotationInfoForm));
        if (typeof func == 'function') {
          func();
        }
      })
    },

    removeQuotation(row) {
      let index = this.quotationInfoForm.quotationInfo.findIndex(item => {
        if (row.tempId) {
          return item.tempId == row.tempId;//新建的或者点击过修改
        } else {
          return item.id == row.id; ////初始化来的还未点击过
        }
      })
      this.quotationInfoForm.quotationInfo.splice(index, 1);
    },
    editQuotation(row) {
      this.quotationDialog.currentEdit = JSON.parse(JSON.stringify(row));
      this.quotationDialog.form = Object.assign({}, row.quotationMap);
      this.quotationDialog.open = true;
      this.quotationDialog.title = "修改报价信息";
    },
    openDialog() {
      this.quotationDialog.currentEdit = {
        opportunityCode: this.oppdata.opportunityCode,
        createBy: this.$store.state.user.name,
      }
      this.quotationDialog.form = { quotationStage: this.stageShow + "", };
      this.quotationDialog.open = true;
      this.quotationDialog.title = "添加报价新信息";
    },
    selectProducted(val) {
      let select = this.dict.type.crm_opportunity_main_product.find(item => {
        return item.value == val
      })
      this.quotationDialog.form.productName = select.label;
      var _this = this;
      this.getNextOptions(select.raw.remark,function(res){
        _this.quotationDialog.productVersion = res;
        _this.quotationDialog.productSecVersion = [];
      })
      this.$set(this.quotationDialog.form, 'productVersion', "");
      this.$set(this.quotationDialog.form, 'productSecVersion', "");
    },
    getNextOptions(dictCode,func){
      this.getDicts(dictCode).then(res=>{        
        if (typeof func == 'function') {
          func(res.data);
        }
        return
      }).catch(error => {
        if (typeof func == 'function') {
          func([]);
        }
      })
    },
    selectProductVersion(val) { 
      this.$set(this.quotationDialog.form, 'productSecVersion', "");
      let select = this.quotationDialog.productVersion.find(item => {
        return item.dictValue == val
      })
      var _this = this;
      this.getNextOptions(select.remark,function(res){
        _this.quotationDialog.productSecVersion = res;
      })
    },
    
    calculation(value) {
      let productCount = (this.quotationDialog.form.productCount || 0) * 1;
      let productPrice = (this.quotationDialog.form.productPrice || 0) * 1;
      let productTotalPrice = (productCount) * (productPrice).toFixed(2);
      this.$set(this.quotationDialog.form, 'productTotalPrice', productTotalPrice);
    },
    submitDialogForm() {
      this.$refs["quotationDialog.form"].validate(valid => {
        if (!valid) {
          return;
        }
        this.quotationDialog.currentEdit.quotationMap = this.quotationDialog.form;
        this.quotationDialog.currentEdit.quotationJson = JSON.stringify(this.quotationDialog.form);
        if (!this.quotationDialog.currentEdit.id && !this.quotationDialog.currentEdit.tempId) {
          this.quotationDialog.currentEdit.tempId = new Date().getTime();//先打个标
          this.quotationInfoForm.quotationInfo.push(this.quotationDialog.currentEdit);
        }
        // 替换
        this.quotationInfoForm.quotationInfo.map((item, i) => {
          //带来的
          if (item.id && item.id == this.quotationDialog.currentEdit.id) {
            this.quotationInfoForm.quotationInfo.splice(i, 1, this.quotationDialog.currentEdit)
          }
          //新增的
          if (item.tempId && item.tempId == this.quotationDialog.currentEdit.tempId) {
            this.quotationInfoForm.quotationInfo.splice(i, 1, this.quotationDialog.currentEdit)
          }
        })
        this.quotationDialog.open = false;
        //正常的复制 界面显示不刷新
        let quotationInfoFormbak = JSON.parse(JSON.stringify(this.quotationInfoForm));
        this.quotationInfoForm = {};
        this.quotationInfoForm = JSON.parse(JSON.stringify(quotationInfoFormbak));
      });
    },
    cancelDialog() {
      this.quotationDialog.open = false;
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      // 暂时校验必填
      if (!this.quotationInfoForm.quotationInfo || this.quotationInfoForm.quotationInfo.length == 0) {
        this.flag.quotationVerify = true
        flag = false
      }
      this.$refs["quotationInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    //提取修改的信息作为提交
    fetchInformation() {
      if (Object.keys(this.quotationInfoOriginBak).length == 0) {
        this.quotationInfoModified.quotationInfo_a = this.quotationInfoForm.quotationInfo
        return;
      }
      this.quotationInfoModified.quotationInfo_a = []
      this.quotationInfoModified.quotationInfo_m = []
      this.quotationInfoModified.quotationInfo_d = []
      for (let options of this.quotationInfoForm.quotationInfo) {
        if (!options.id) {
          this.quotationInfoModified.quotationInfo_a.push(options)
          continue;
        }
        let currentOld = this.quotationInfoOriginBak.quotationInfo.find(item => { return item.id == options.id; })
        if (this.objEqual(currentOld, options)) {
          continue;
        }
        this.quotationInfoModified.quotationInfo_m.push(options)
      }
      for (let options of this.quotationInfoOriginBak.quotationInfo) {
        let current = this.quotationInfoForm.quotationInfo.find(item => { return item.id == options.id; })
        if (current) {
          continue;
        }
        this.quotationInfoModified.quotationInfo_d.push(options)
      }
    },
    //对象属性全部平铺的情况
    objEqual(oldObj, newObj) {
      for (let key in oldObj) {
        // 不考虑new新加的标识属性
        if (typeof oldObj[key] == 'object') {
          if (!this.objEqual(oldObj[key], newObj[key])) {
            return false;
          }
          continue;
        }
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
        currentData: this.quotationInfoForm,//最新展示数据
        modifyedData: this.quotationInfoModified,//改动部分
        originData: this.quotationInfoOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped>
#quotationVerify {
  color: #ff4949;
  font-size: 12px;
  line-height: 3;
}
</style>
