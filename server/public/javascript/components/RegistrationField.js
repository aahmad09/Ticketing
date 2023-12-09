const ce = React.createElement;

class RegistrationField extends React.Component {
    constructor(props) {
        super(props);
        this.submit = props.submit;
        this.state = {
            name: "",
            email: "",
            password: "",
            role: ""
        };
    }

    handleChange(field, e) {
        const val = e.target.value;
        this.setState((s) => ({ ...s, [field]: val }));
    }

    handleSubmit(e) {
        this.submit({ ...this.state });
    }

    render() {
        return ce('div', null, 
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field', type: 'text', placeholder: 'NAME', value: this.state.name, onChange: (e) => this.handleChange('name', e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field', placeholder: 'EMAIL', type: 'text', value: this.state.email, onChange: (e) => this.handleChange('email', e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field', placeholder: 'PASSWORD', type: 'password', value: this.state.password, onChange: (e) => this.handleChange('password', e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field', type: 'text', placeholder: '', value: this.state.role, onChange: (e) => this.handleChange('role', e)}),
            ),
            ce('button', {className: 'login_button', onClick: (e) => this.handleSubmit(e)}, "Submit"),
        );
    }
}

export default RegistrationField;
