<template>
    <v-menu
        v-model="show"
        :close-on-content-click="false"
        :nudge-right="40"
        transition="scale-transition"
        offset-y
        min-width="auto"
    >
        <template v-slot:activator="{ on, attrs }">
            <v-text-field
                :value="value"
                @input="onInput"
                :label="label"
                :prepend-inner-icon="iconName"
                readonly
                v-bind="attrs"
                v-on="on"
            ></v-text-field>
        </template>
        <v-date-picker
            :value="value"
            @input="onInput"
        ></v-date-picker>
    </v-menu>
</template>

<script>
export default {
    name: "DatePicker",
    props: {
        value: {
            required: true,
        },
        label: {
            type: String,
            required: true,
        },
        showIcon: {
            type: Boolean,
            default: false,
        }
    },
    data() {
        return {
            show: false,
        };
    },
    computed: {
        iconName() {
            return this.showIcon ? 'mdi-calendar' : '';
        }
    },
    methods: {
        onInput(value) {
            this.$emit('input', value);
            this.show = false;
        }
    },
}
</script>
