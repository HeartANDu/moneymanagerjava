export default {
    computed: {
        $v() {
            return {
                required() {
                    return v => !!v || `Field is required`;
                },
                between(min, max) {
                    return v => (!!v && typeof v === 'string' && v.length >= min && v.length <= max)
                        || `Field must be between ${min} and ${max}`;
                },
                minLength(min) {
                    return v => (!!v && typeof v === 'string' && v.length >= min)
                        || `Field must be at least ${min} characters long`;
                },
                maxLength(max) {
                    return v => (!!v && typeof v === 'string' && v.length <= max)
                        || `Field must be less than ${max} characters long`;
                },
                email() {
                    return v => (!!v && typeof v === 'string' && !!v.match(/^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/))
                        || `Field must be valid email`;
                },
                sameAs(otherField, otherValue) {
                    return v => v === otherValue || `Field must be the same as ${otherField}`;
                },
            };
        }
    },
}
