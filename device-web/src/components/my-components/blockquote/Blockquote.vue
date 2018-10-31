<template>
    <div class="page-bar">
        <div class="elem-quote" :style="cl">
            <slot name="txt"></slot>
        </div>
        <div>
            <slot name="btns"></slot>
        </div>
    </div>

</template>
<style>
    .elem-quote{
        height: 20px;
        line-height: 20px;
        color: rgb(51, 51, 51);
        font-size: 18px;
        font-weight: bold;
        padding-left: 12px;
        border-left: 4px solid rgb(102, 102, 102);
    }
    .page-bar{
        display:flex;
        justify-content: space-between;
    }
</style>
<script>
    export default {
        props: [
            "color"
        ],
        directives: {

        },
        data() {
            return {
                cl:"border-left-color:#"+this.color,
            }
        },
        watch: {
            cl(newVal) {
                this.$emit('update', newVal)
            }
        }
    }

</script>

