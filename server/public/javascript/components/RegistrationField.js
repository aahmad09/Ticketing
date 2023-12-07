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
                'Name: ',
                ce('input', {type: 'text', value: this.state.name, onChange: (e) => this.handleChange('name', e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                'Email: ',
                ce('input', {type: 'text', value: this.state.email, onChange: (e) => this.handleChange('email', e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                'Password: ',
                ce('input', {type: 'password', value: this.state.password, onChange: (e) => this.handleChange('password', e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                'Role: ',
                ce('input', {type: 'text', value: this.state.role, onChange: (e) => this.handleChange('role', e)}),
            ),
            ce('button', {onClick: (e) => this.handleSubmit(e)}, "Submit"),
        );
    }
}

export default RegistrationField;
