const ce = React.createElement

class ProfileDropdown extends React.Component {
    constructor(props) {
        super(props)
        this.img = props.img
        this.state = {open: false}
    }

    toggleDropdown = (e) => {
        const val = e.target.value;
        this.setState((s) => {return {...s, open: !this.state.open}});
    }

    render() {
        return ce('div', {onClick: this.toggleDropdown}, 
            "HI",
        )
    }
}

export default ProfileDropdown