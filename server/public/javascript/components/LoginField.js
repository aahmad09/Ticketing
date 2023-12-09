const ce = React.createElement

class LoginField extends React.Component {
    constructor(props) {
        super(props)
        this.submit = props.submit
        this.state = {email: "", password: ""}
    }

    handleEmailChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, email: val}});
    }
    handlePasswordChange(e) {
        const val = e.target.value;
        this.setState((s) => {return {...s, password: val}});
    }
    handleSubmit(e) {
        this.submit({...this.state});
    }

    render() {
        return ce('div', null, 
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field', type: 'text', placeholder: 'EMAIL', value: this.state.email, onChange: (e) => this.handleEmailChange(e)}),
            ),
            ce('div', {className: 'labeled_field'}, 
                ce('input', {className: 'text_field', type: 'password', placeholder: 'PASSWORD', value: this.state.password, onChange: (e) => this.handlePasswordChange(e)}),
            ),
            ce('button', {className: 'login_button', onClick: (e) => this.handleSubmit(e)}, "Submit"),
        );
    }
}


export default LoginField
