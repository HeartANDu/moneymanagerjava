<template>
    <div>
        <h4 v-if="message">{{ message }} <a href="#" @click.prevent="reset">Reset</a></h4>
        <h4 v-else>No message yet. <a href="#" @click.prevent="getMessage">Get one</a></h4>
    </div>
</template>

<script>
export default {
    name: 'CorsTest',
    data() {
        return {
            loading: false,
            message: null,
        };
    },
    methods: {
        getMessage() {
            axios.get('message')
                .then(response => {
                    this.message = response.data.message;
                });
        },
        reset() {
            this.message = null;
        },
    }
}
</script>
