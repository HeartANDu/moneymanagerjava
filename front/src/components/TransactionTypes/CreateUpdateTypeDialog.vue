<template>
    <v-dialog v-model="show" max-width="400">
        <template v-slot:activator="{on, attrs}">
            <v-btn color="primary"
                   v-bind="attrs"
                   @click="showDialog()"
            >
                Create
            </v-btn>
        </template>

        <v-card>
            <v-card-title>
                {{ actionText }} transaction type
            </v-card-title>

            <v-card-text>
                <v-form ref="form">
                    <v-text-field label="Name"
                                  v-model="type.name"
                                  required
                    />

                    <v-select label="Action Type"
                              :items="transactionActions"
                              v-model="type.action"
                    />
                </v-form>
            </v-card-text>

            <v-divider />

            <v-card-actions>
                <v-btn color="default"
                       text
                       @click="hideDialog"
                >
                    Close
                </v-btn>
                <v-spacer />
                <v-btn color="primary"
                       text
                       @click="save"
                >
                    Save
                </v-btn>
            </v-card-actions>
        </v-card>
    </v-dialog>
</template>

<script>
import {clone} from 'lodash';

const DEFAULT_TYPE = {
    id: null,
    name: null,
    action: null,
};

const URL = '/transaction-types';

export default {
    name: "CreateUpdateTypeDialog",
    computed: {
        actionText() {
            return this.type?.id ? 'Edit' : 'Create';
        },
        transactionActions() {
            return this.$store.state.transactionActions.items;
        },
    },
    data() {
        return {
            show: false,
            type: clone(DEFAULT_TYPE),
        };
    },
    methods: {
        reset() {
            this.type = clone(DEFAULT_TYPE);
        },
        initType(type) {
            if (!type) {
                this.reset();
            } else {
                this.type.id = type.id;
                this.type.name = type.name;
                this.type.action = type.action;
            }
        },
        showDialog(type) {
            this.show = true;
            this.initType(type);
        },
        hideDialog() {
            this.show = false;
        },

        save() {
            this.loading = true;

            let id = this.type.id;
            let data = {
                name: this.type.name,
                action: this.type.action,
            };
            let url = id ? URL + `/${id}` : URL;

            this.$http.request({method: id ? 'put' : 'post', url, data})
                .then(() => {
                    this.$root.success('Transaction type save successfully');
                    this.$emit('refresh');
                    this.hideDialog();
                })
                .catch(this.$root.responseError)
                .finally(() => this.loading = false);
        },
    },
    mounted() {
        this.$store.dispatch('transactionActions/init');
    },
}
</script>
