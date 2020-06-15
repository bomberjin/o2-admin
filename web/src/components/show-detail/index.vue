<template>
  <div>
    <el-dialog
    title="详情"
    :close-on-click-modal="false"
    :width="width"
    :visible.sync="visible">

    <el-form :model="dataForm" class="show-detail" ref="dataForm" label-width="80px">
      <el-row v-for="(a,i) in detailFormItems" :key="i">
        <el-col :span="parseInt(24/a.colNum)" v-for="(b,j) in a.cols" :key="`col${j}`">
          <el-form-item :label="b.label" :prop="b.prop">
            <render-slot v-if="b.render" :render="b.render" :rowData="dataForm[b.prop]"></render-slot>
            <el-input v-else v-model="dataForm[b.prop]" readonly></el-input>
          </el-form-item>
        </el-col>
      </el-row>
    </el-form>

    <span slot="footer" class="dialog-footer">
      <el-button type="primary" @click="visible = false">确定</el-button>
    </span>
    </el-dialog>
  </div>
</template>

<script>
import RenderSlot from '../renderSlot'
export default {
  props: {
    detailData: Object,
    detailFormItems: Array,
    width: {
      type: String,
      default: '50%'
    }
  },
  data () {
    return {
      visible: false,
      dataForm: {}
    }
  },
  components: {
    RenderSlot
  }
}
</script>

<style lang="scss">
  .show-detail .el-form-item__label{
    overflow: hidden;
    text-overflow:ellipsis;
    white-space:nowrap;
  }
</style>
