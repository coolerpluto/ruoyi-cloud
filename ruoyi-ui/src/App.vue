<template>
  <div id="app">
    <router-view />
    <theme-picker />
  </div>
</template>

<script>
import ThemePicker from "@/components/ThemePicker";
import watermark from "watermark-dom";

export default {
  name: "App",
  components: { ThemePicker },
  mounted() {
    // 加载水印
    let _this=this
    this.timerWatermark = setInterval(function () {
        const username = _this.$store.state.user.name;
        watermark.load({
          watermark_txt: username +"@"+ new Date().toLocaleString() ,
          watermark_x:140,                     //水印起始位置x轴坐标
          watermark_y:150,                     //水印起始位置Y轴坐标
          watermark_x_space:100,              //水印x轴间隔
          watermark_y_space:50,               //水印y轴间隔
          watermark_fontsize:'20px',
          watermark_alpha:0.15,
          watermark_angle:30,
          watermark_width:300,
          watermark_height:300,
        });
    })
  },
  // 销毁时清除计时器
  beforeDestroy: function () {
    if (this.timerWatermark) {
      clearInterval(this.timerWatermark)
    }
  },
  metaInfo() {
    return {
      title: this.$store.state.settings.dynamicTitle && this.$store.state.settings.title,
      titleTemplate: title => {
        return title ? `${title} - ${process.env.VUE_APP_TITLE}` : process.env.VUE_APP_TITLE
      }
    }
  }
};
</script>
<style scoped>
#app .theme-picker {
  display: none;
}
</style>
