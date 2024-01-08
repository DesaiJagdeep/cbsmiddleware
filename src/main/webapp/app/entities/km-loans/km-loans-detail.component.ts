import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { KmLoans } from './km-loans.model';
import { KmLoansService } from './km-loans.service';

@Component({
    selector: 'jhi-km-loans-detail',
    templateUrl: './km-loans-detail.component.html'
})
export class KmLoansDetailComponent implements OnInit, OnDestroy {

    kmLoans: KmLoans;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kmLoansService: KmLoansService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKmLoans();
    }

    load(id) {
        this.kmLoansService.find(id).subscribe((kmLoans) => {
            this.kmLoans = kmLoans;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKmLoans() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kmLoansListModification',
            (response) => this.load(this.kmLoans.id)
        );
    }
}
