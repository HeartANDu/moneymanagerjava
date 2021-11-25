<template>
    <v-dialog v-model="show" max-width="400">
        <v-card>
            <v-card-title class="text-center" style="word-break: keep-all;">
                {{ title }}
            </v-card-title>
            <v-card-text v-html="message" v-if="message" />
            <v-card-actions>
                <v-btn color="default" text @click="onDeny">Cancel</v-btn>
                <v-spacer />
                <v-btn color="primary" text @click="onConfirm">Confirm</v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
export default {
    name: "ConfirmDialog",
    data() {
        return {
            show: false,
            title: null,
            message: null,
            confirm: null,
            deny: null,
        };
    },
    methods: {
        reset() {
            this.title = null;
            this.message = null;
            this.confirm = null;
            this.deny = null;
        },
        showDialog(title, message, confirm, deny) {
            this.show = true;
            this.title = title;
            this.message = message;
            this.confirm = confirm;
            this.deny = deny;
        },
        hideDialog() {
            this.show = false;
            this.reset();
        },

        onConfirm() {
            if (typeof this.confirm === 'function') {
                this.confirm();
            }

            this.hideDialog();
        },
        onDeny() {
            if (typeof this.deny === 'function') {
                this.deny();
            }

            this.hideDialog();
        },
    }
}
</script>
