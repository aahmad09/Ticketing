const ce = React.createElement

class EventDetails extends React.Component {
    constructor(props) {
        super(props)
        console.log(props.data)
        this.ticketId = props.data.ticketId
        this.eventId = props.data.eventId
        this.orgId = props.data.orgId
        this.name = props.data.name
        this.image = props.data.image
        this.location = props.data.location
        this.description = props.data.description
        this.date = props.data.date
        this.color = props.data.color
        this.togglePopup = props.data.togglePopup
    }

    handlePurchase(){
        TODO
    }
    render() {
        return ce('div', {className: 'eventDetail_area'}, 
            /*translate between orgID and org name here */ 
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.orgId),
            ),
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.name),
            ),
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.date),
            ),
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.location),
            ),
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.description),
            ),
            ce('div', {className: 'detail'}, 
                ce('h4', {className: 'detail_text'},this.image),
            ),
            ce('button',{className:'purchase-buttton',onClick: (e) => this.handlePurchase(e)},"purchase a ticket ->")
           
        );
    }
}

export default EventDetails