<template>
  <div class="app-container" :style="{ pointerEvents: oppdata.action == 'V' ? 'none' : 'unset' }">
    <h3>{{ stageTitle }}</h3><el-divider />
    <el-form ref="summaryInfoForm" :rules="rules" v-loading="flag.summaryInfoLoading" :model="summaryInfoForm" size="medium" label-width="220px">
      <div v-if="[2].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="8">
            <el-form-item label="后续动作" prop="followAction2.propertyVal">
              <el-radio v-model="summaryInfoForm.followAction2.propertyVal" label="Y">参与</el-radio>
              <el-radio v-model="summaryInfoForm.followAction2.propertyVal" label="N">放弃</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="后续跟进措施或原因" prop="followAction2Desc.propertyVal">
              <el-input v-model="summaryInfoForm.followAction2Desc.propertyVal" type="textarea" resize="none" :rows="3"
                placeholder="后续跟进措施或原因应小于500个字" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="[4].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="20">
            <el-form-item label="后续动作" prop="followAction4.propertyVal">
              <el-radio v-model="summaryInfoForm.followAction4.propertyVal" label="T5">中标(转L5)</el-radio>
              <el-radio v-model="summaryInfoForm.followAction4.propertyVal" label="T6">弃标(转L6)</el-radio>
              <el-radio v-model="summaryInfoForm.followAction4.propertyVal" label="T7">项目终止(转L7)</el-radio>
              <el-radio v-model="summaryInfoForm.followAction4.propertyVal" label="T8">丢标(转L8)</el-radio>
              <el-radio v-model="summaryInfoForm.followAction4.propertyVal" label="T11">关闭商机(转L11)</el-radio>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="后续跟进措施或原因" prop="followAction4Desc.propertyVal">
              <el-input v-model="summaryInfoForm.followAction4Desc.propertyVal" type="textarea" resize="none" :rows="3"
                placeholder="后续跟进措施或原因应小于500个字" clearable>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </div>
      <div v-if="[5, 6, 7, 8, 9, 11].includes(stageShow)">
        <el-row :gutter="15">
          <el-col :span="24">
            <el-form-item label="总结" prop="summaryInfo.propertyVal">
              <el-input v-model="summaryInfoForm.summaryInfo.propertyVal" type="textarea" resize="none" :rows="3"
                placeholder="请输入项目总结经验,小于500个字" clearable>
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
  name: "summaryInfo",
  dicts: [],
  props: {
    stageShow: {
      type: Number,
      default: 2
    },
    stageTitle: {
      type: String,
      default: '总结'
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
        "followAction2", "followAction2Desc",
        "followAction4", "followAction4Desc",
        "summaryInfo"
      ],
      summaryInfoForm: {
        "followAction2": {}, "followAction2Desc": {},
        "followAction4": {}, "followAction4Desc": {},
        "summaryInfo": {}
      },
      summaryInfoModified: {},
      summaryInfoOriginBak: {},
      //标识集合
      flag: {
        summaryInfoLoading: false,
        summaryInfoVerify: false,
      },
      rules: {
        "followAction2": {
          propertyVal: [
            { required: true, message: "未选择 后续动作", trigger: "blur" }
          ],
        },
        "followAction2Desc": {
          propertyVal: [
            { required: true, message: "未填写 后续跟进措施或原因", trigger: "blur" }
          ],
        },
        "followAction4": {
          propertyVal: [
            { required: true, message: "未选择 后续动作", trigger: "blur" }
          ],
        },
        "followAction4Desc": {
          propertyVal: [
            { required: true, message: "未填写 后续跟进措施或原因", trigger: "blur" }
          ],
        },
        "summaryInfo": {
          propertyVal: [
            { required: true, message: "未填写 总结信息", trigger: "blur" }
          ],
        },
      }
    }
  },
  created() {
    this.initSummaryInfo();
  },
  methods: {
    initSummaryInfo() {
      var _this = this;
      this.flag.summaryInfoLoading = true;
      this.getProperties(function () {
        //console.log("summaryInfoForm:",_this.summaryInfoForm)
        _this.flag.summaryInfoLoading = false;
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
          model: "results",
        }
      }
      getPropertiesMap(req).then(response => {
        if (response.code !== 200) {
          this.$modal.msgError(response.msg);
          return
        }
        this.summaryInfoForm = response.data;
        this.summaryInfoOriginBak = JSON.parse(JSON.stringify(this.summaryInfoForm))
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
      this.$refs["summaryInfoForm"].validate(valid => {
        if (!valid) {
          flag = false
        }
      });
      return flag;
    },
    // 提供本组件的全部数据
    collectInfo() {
      return {
        currentData: this.summaryInfoForm,//最新展示数据
        modifyedData: this.summaryInfoModified,//改动部分
        originData: this.summaryInfoOriginBak,//原始数据
      };
    },
  },
}
</script>

<style scoped></style>
