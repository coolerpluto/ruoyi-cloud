<template>
  <div class="app-container">
    <!--style="pointer-events: none;"-->
    <el-steps :active="stageActive" size="small" align-center simple>
      <el-step title="L1.发现机会" v-if="stageShowList.includes(1)" icon="el-icon-plus" @click.native="changeStage(1)"/>
      <el-step title="L2.分析机会" v-if="stageShowList.includes(2)" icon="el-icon-document" @click.native="changeStage(2)"/>
      <el-step title="L3.落实机会" v-if="stageShowList.includes(3)" icon="el-icon-document-checked" @click.native="changeStage(3)"/>
      <el-step title="L4.竞标阶段" v-if="stageShowList.includes(4)"icon="el-icon-s-cooperation" @click.native="changeStage(4)"/>
      <el-step title="L5.合同签订" v-if="stageShowList.includes(5)" icon="el-icon-picture" @click.native="changeStage(5)"/>
      <el-step title="L6.弃标分析" v-if="stageShowList.includes(6)" icon="el-icon-chat-dot-square" @click.native="changeStage(6)"/>
      <el-step title="L7.项目停止" v-if="stageShowList.includes(7)" icon="el-icon-chat-dot-square" @click.native="changeStage(7)"/>
      <el-step title="L8.丢标分析" v-if="stageShowList.includes(8)" icon="el-icon-chat-dot-square" @click.native="changeStage(8)"/>
      <el-step title="L9.完成合同" v-if="stageShowList.includes(9)" icon="el-icon-s-check" @click.native="changeStage(9)"/>
      <el-step title="L11.关闭商机" v-if="stageShowList.includes(11)" icon="el-icon-document-delete" @click.native="changeStage(11)"/>
    </el-steps>
    <div style="text-align: center;">
      <el-dropdown v-if="![1].includes(stageActive)" @command="handlePreStageCommand">
        <el-button type="primary">
          <i class="el-icon-arrow-left el-icon--left"></i>
          {{ targetPreStageName }}
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="item in targetPreStageList" :command="item.value">{{
              item.label
            }}
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <el-button-group>
        <el-button type="primary" v-show="![1].includes(stageActive)" @click="changeStage(targetPreStage)">
          <i class="el-icon-arrow-left el-icon--left"></i>
          <i class="el-icon-arrow-left el-icon--left"></i>
          上一步
        </el-button>
        <el-button type="primary" icon="el-icon-unlock" v-if="[8,7,11].includes(stageActive)" @click="changeStage(2)">
          重新激活到L2
        </el-button>
        <el-button type="primary" v-show="![8,7,9,11].includes(stageActive)" @click="changeStage(targetNextStage)">
          下一步
          <i class="el-icon-arrow-right el-icon--right"></i>
          <i class="el-icon-arrow-right el-icon--right"></i>
        </el-button>
      </el-button-group>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <el-dropdown :v-show="![9,8,7,11].includes(stageActive)" @command="handleNextStageCommand">
        <el-button type="primary">
          {{ targetStageName }}
          <i class="el-icon-arrow-right el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="item in targetNextStageList" :command="item.value">{{
              item.label
            }}
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
    <stage-l01 v-if="stageActive ===1"/>
    <stage-l02 v-if="stageActive ===2"/>
    <stage-l03 v-if="stageActive ===3"/>
    <stage-l04 v-if="stageActive ===4"/>
    <stage-l05 v-if="stageActive ===5"/>
    <stage-l06 v-if="stageActive ===6"/>
    <stage-l07 v-if="stageActive ===7"/>
    <stage-l08 v-if="stageActive ===8"/>
    <stage-l09 v-if="stageActive ===9"/>
    <stage-l11 v-if="stageActive ===11"/>
    <div style="text-align: center;">
      <el-dropdown v-if="![1].includes(stageActive)" @command="handlePreStageCommand">
        <el-button type="primary">
          <i class="el-icon-arrow-left el-icon--left"></i>
          {{ targetPreStageName }}
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="item in targetPreStageList" :command="item.value">{{
              item.label
            }}
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <el-button-group>
        <el-button type="primary" v-show="![1].includes(stageActive)" @click="changeStage(targetPreStage)">
          <i class="el-icon-arrow-left el-icon--left"></i>
          <i class="el-icon-arrow-left el-icon--left"></i>
          上一步
        </el-button>
        <el-button type="primary" icon="el-icon-unlock" v-if="[8,7,11].includes(stageActive)" @click="changeStage(2)">
          重新激活到L2
        </el-button>
        <el-button type="primary" v-show="![8,7,9,11].includes(stageActive)" @click="changeStage(targetNextStage)">
          下一步
          <i class="el-icon-arrow-right el-icon--right"></i>
          <i class="el-icon-arrow-right el-icon--right"></i>
        </el-button>
      </el-button-group>
      &nbsp;&nbsp;&nbsp;&nbsp;
      <el-dropdown :v-show="![9,8,7,11].includes(stageActive)" @command="handleNextStageCommand">
        <el-button type="primary">
          {{ targetStageName }}
          <i class="el-icon-arrow-right el-icon--right"></i>
        </el-button>
        <el-dropdown-menu slot="dropdown">
          <el-dropdown-item v-for="item in targetNextStageList" :command="item.value">{{
              item.label
            }}
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>
    </div>
  </div>
</template>

<script>
// 引入所有需要的动态组件 以 "stage" 开头,以".vue"结尾
const stageComponent = require.context(
  "./opportunity_stage", //组件所在目录的相对路径
  false, //是否查询其子目录
  /\.vue$/ //匹配基础组件文件名的正则表达式
);
var comObj = {};
stageComponent.keys().forEach(fileName => {
  // 获取文件名
  var names = fileName
    .split("/")
    .pop()
    .replace(/\.vue$/, "");
  // 获取组件配置
  const componentConfig = stageComponent(fileName);
  // 若该组件是通过"export default"导出的，优先使用".default"，否则退回到使用模块的根
  comObj[names] = componentConfig.default || componentConfig;
});

export default {
  name: "detailData",
  dicts: ['crm_opportunity_status'],
  components: comObj,
  data() {
    return {
      stageActive: 1,
      modelActive: 1,
      stageShowList:[1],
      targetNextStage: 0,// 向下新的阶段
      targetStageName: '选择阶段',
      targetNextStageList: [
        {value: 1, label: "L1.新建商机"},
        {value: 2, label: "L2.分析机会"},
        {value: 3, label: "L3.落实机会"},
        {value: 4, label: "L4.竞标"},
        {value: 5, label: "L5.合同签订"},
        {value: 6, label: "L6.弃标分析"},
        {value: 7, label: "L7.项目停止"},
        {value: 8, label: "L8.丢标分析"},
        {value: 9, label: "L9.完成合同"},
        {value: 11, label: "L11.关闭商机"},
      ],// 向下新可选的阶段

      targetPreStage: 0,// 向上新的阶段
      targetPreStageName: '选择阶段',
      targetPreStageList: [
        {value: 1, label: "L1.新建商机"},
        {value: 2, label: "L2.分析机会"},
        {value: 3, label: "L3.落实机会"},
        {value: 4, label: "L4.竞标"},
        {value: 5, label: "L5.合同签订"},
        {value: 6, label: "L6.弃标分析"},
        {value: 7, label: "L7.项目停止"},
        {value: 8, label: "L8.丢标分析"},
        {value: 9, label: "L9.完成合同"},
        {value: 11, label: "L11.关闭商机"},
      ],// 向上新可选的阶段

    };
  },
  created() {
    const opportunityId = this.$route.params && this.$route.params.id;
    //console.log("opportunityId",opportunityId)
    const query = this.$route.query;//参考代码生产传值
    //console.log("query",query)
  },
  methods: {
    getList() {
    },
    changeStage(targetStage) {
      if(this.stageActive === targetStage){
        this.$modal.msg("已在选择目标阶段，无需跳转");
      }else if(targetStage ===0){
        this.$modal.alert("您还未选择目标阶段或者目标阶段选择错误！")
      }else {
        this.stageActive = targetStage;
        this.stageShowList.push(targetStage)
        this.targetPreStageName = '选择阶段'
        this.targetPreStage = 0
        this.targetStageName = '选择阶段'
        this.targetNextStage = 0
      }
    },
    handlePreStageCommand(command) {
      let preStageInfo = this.targetPreStageList.find(item => item.value === command)
      this.targetPreStageName = preStageInfo.label
      this.targetPreStage = preStageInfo.value
    },
    handleNextStageCommand(command) {
      let nextStageInfo = this.targetNextStageList.find(item => item.value === command)
      console.log(nextStageInfo)
      this.targetStageName = nextStageInfo.label
      this.targetNextStage = nextStageInfo.value
    },

  }
}
</script>

<style scoped>
.el-step__title {
  font-size: 13px;
}
</style>
