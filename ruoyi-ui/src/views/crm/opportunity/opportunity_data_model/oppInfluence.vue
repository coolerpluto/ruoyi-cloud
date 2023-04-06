<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>产生的影响</h3>
    <el-divider />
    <el-form ref="oppInfluenceForm" v-loading="flag.oppInfluenceLoading" :rules="rules" :model="oppInfluenceForm"
      size="medium" label-width="220px">
      <div v-if="[2].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="对用户组织架构及作用说明" prop="oppInfluence.propertyVal">
              <el-input v-model="oppInfluenceForm.oppInfluence.propertyVal" type="textarea" resize="none" :rows="3"
                placeholder="对用户组织架构及作用的补充说明应小于500个字" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
    </el-form>
  </div>
</template>

<script>
import { getPropertiesMap } from "@/api/crm/oppUnitedInfo"

export default {
  name: "oppInfluence",
  dicts: [],
  props: {
    stageShow: {
      type: Number,
      default: 2
    },
    oppdata: {
      type: Object,
      default() {
        return {}
      }
    }
  },
  data() {
    return {
      propertyKeys: [
        "oppInfluence"
      ],
      oppInfluenceForm: {
        "oppInfluence": {model: "oppo_influence", propertyLable: "对用户组织架构及作用说明" }
      },
      oppInfluenceModified: {},
      oppInfluenceOriginBak: {},
      //标识集合
      flag: {
        oppInfluenceLoading: false,
        oppInfluenceVerify: false,
      },
      rules: {
        "oppInfluence": {
          propertyVal: [
            { required: true, message: "未填写 对用户组织架构及作用说明", trigger: "blur" }
          ],
        },
      }
    }
  },
  created() {
    this.initOppInfluence();
  },
  methods: {
    initOppInfluence() {
      var _this = this;
      this.flag.oppInfluenceLoading = true;
      this.getProperties(function () {
        //console.log("oppInfluenceForm:",_this.oppInfluenceForm)
        _this.flag.oppInfluenceLoading = false;
      })
    },
    // 商机属性查询
    getProperties(func) {
      if (!this.oppdata.opportunityCode || this.oppdata.opportunityCode === '0') {
        if (typeof func == 'function') {
          func();
        }
        return
      }
      let req = {
        code: this.oppdata.opportunityCode,
        params: {
          propertyKeys: this.propertyKeys,
          model: "oppo_influence",
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.oppInfluenceForm = response.data;
        this.oppInfluenceOriginBak = JSON.parse(JSON.stringify(this.oppInfluenceForm))
        if (typeof func == 'function') {
          func();
        }
      }).catch(error => {
        if (typeof func == 'function') {
          func();
        }
      })
    },
    // 提供本组件的数据校验
    infoVerify() {
      let flag = true
      this.$refs["oppInfluenceForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    //提取修改的信息作为提交
    fetchInformation() {
      if (Object.keys(this.oppInfluenceOriginBak).length == 0) {
        this.oppInfluenceModified = JSON.parse(JSON.stringify(this.oppInfluenceForm))
        return;
      }
      this.oppInfluenceModified = {}
      Object.keys(this.oppInfluenceForm).forEach(key => {
        if (this.oppInfluenceOriginBak[key].propertyVal != this.oppInfluenceForm[key].propertyVal) {
          this.oppInfluenceModified[key] = this.oppInfluenceForm[key]
        }
      })
    },
    // 提供本组件的全部数据
    collectInfo() {
      this.fetchInformation();
      return {
        currentData: this.oppInfluenceForm,//最新展示数据
        modifyedData: this.oppInfluenceModified,//改动部分
        originData: this.oppInfluenceOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped></style>
