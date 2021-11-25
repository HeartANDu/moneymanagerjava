<template>
    <v-app>
        <router-view />
        <confirm-dialog ref="confirm" />
    </v-app>
</template>

<script>
import ConfirmDialog from "@/components/Common/ConfirmDialog";

export default {
    components: {ConfirmDialog},
    methods: {
        showConfirmDialog(title, message, confirm, deny) {
            this.$refs.confirm.showDialog(title, message, confirm, deny);
        }
    }
}
</script>
