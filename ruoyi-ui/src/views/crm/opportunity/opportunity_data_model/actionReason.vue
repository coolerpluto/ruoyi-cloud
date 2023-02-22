<template>
  <div class="app-container">
    <h3>{{ stageTitle }}（必须勾选三项或以上）</h3>
    <el-divider/>
    <el-form ref="actionReasonForm" :model="actionReasonForm" size="medium" label-width="220px">
      <el-row :gutter="15">
        <el-checkbox-group v-model="actionReasonForm.reasons" @change="changeReasonSelect">
          <el-col v-for="item in reasonsList" :key="item.dictCode" :span="8">
            <el-checkbox :label="item.dictValue">
              {{ item.dictLabel }}
            </el-checkbox>
          </el-col>
        </el-checkbox-group>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import {getDicts as getDicts} from '@/api/system/dict/data'

export default {
  name: "actionReason",
  props: {
    stageShow: {
      type: Number,
      default: 5
    },
    stageTitle: {
      type: String,
      default: '原因分析'
    },
    reasonDictCode: {
      type: String,
      default: 'crm_opportunity_finish_reason'
    },
  },
  data() {
    return {
      actionReasonForm: {
        stage: this.stageShow,
        reasons: [],
      },
      reasonsList: [],
    }
  },
  created() {
    this.initReasonList();
  },
  methods: {
    initReasonList() {
      getDicts(this.reasonDictCode).then(res => {
        this.reasonsList = res.data;
      }).catch(error => {
        this.reasonsList = []
      })
    },
    changeReasonSelect() {
    }
  },
}
</script>

<style scoped>

</style>
