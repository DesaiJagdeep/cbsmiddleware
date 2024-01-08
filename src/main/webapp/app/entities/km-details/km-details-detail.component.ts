import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { KmDetails } from './km-details.model';
import { KmDetailsService } from './km-details.service';

@Component({
    selector: 'jhi-km-details-detail',
    templateUrl: './km-details-detail.component.html'
})
export class KmDetailsDetailComponent implements OnInit, OnDestroy {

    kmDetails: KmDetails;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kmDetailsService: KmDetailsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKmDetails();
    }

    load(id) {
        this.kmDetailsService.find(id).subscribe((kmDetails) => {
            this.kmDetails = kmDetails;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKmDetails() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kmDetailsListModification',
            (response) => this.load(this.kmDetails.id)
        );
    }
}
