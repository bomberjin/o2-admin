<template>
  <el-dialog
    :title="dataForm.id === 0 ? '新增' : '修改'"
    :close-on-click-modal="false"
    :width="width"
    :visible.sync="visible"
  >
    <el-form :model="dataForm" class="add-or-update-form" ref="dataForm" @keyup.enter.native="dataFormSubmit()" label-width="80px">
      <el-form-item v-for="(a,i) in formItems" :key="i" :label="a.label" :prop="a.prop">
        <render-slot v-if="a.slotFormItem" :render="a.slotFormItem.render" :rowData="dataForm[a.prop]"></render-slot>
        <el-input v-else v-model="dataForm[a.prop]" :placeholder="`请输入${a.label}`"></el-input>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
      <el-button @click="visible = false">取消</el-button>
      <el-button type="primary" @click="dataFormSubmit()">确定</el-button>
    </span>
  </el-dialog>
</template>

<script>
import RenderSlot from '../renderSlot'
export default {
  props: {
    formItems: Array,
    saveOrUpdateUrl: String,
    getFormDateUrl: String,
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
  },
  created () {
    let obj = {}
    this.formItems.forEach(a => {
      obj = {...obj, ...{[a.prop]: ''}}
    })
    this.dataForm = obj
  },
  methods: {
    async init (id) {
      this.dataForm.id = id || 0
      this.visible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].resetFields()
      })

      if (this.dataForm.id) {
        let json = await this.$http({
          url: this.$http.adornUrl(`${this.getFormDateUrl}`),
          method: 'post',
          params: this.$http.adornParams({id: this.dataForm.id})
        })
        const { data } = json
        if (data && data.code === 0) {
          this.dataForm = JSON.parse(JSON.stringify(data.data))
          this.dataForm.id = id
        }
      }

      if (this.$emit('initCallBack')) {
        this.$emit('initCallBack', this.dataForm)
      }
    },
    // 表单提交
    dataFormSubmit () {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          this.$http({
            url: this.$http.adornUrl(`${this.saveOrUpdateUrl}/${!this.dataForm.id ? 'save' : 'update'}`),
            method: 'post',
            data: this.$http.adornData(this.dataForm)
          }).then(({data}) => {
            if (data && data.code === 0) {
              this.$message({
                message: '操作成功',
                type: 'success',
                duration: 1500,
                onClose: () => {
                  this.visible = false
                  this.$emit('refreshDataList')
                }
              })
            } else {
              this.$message.error(data.msg)
            }
          })
        }
      })
    }
  }
}
</script>

<style lang="scss">
  .add-or-update-form .el-form-item__label{
    overflow: hidden;
    text-overflow:ellipsis;
    white-space:nowrap;
  }
</style>
