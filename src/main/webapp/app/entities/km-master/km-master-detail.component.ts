import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { KmMaster } from './km-master.model';
import { KmMasterService } from './km-master.service';

@Component({
    selector: 'jhi-km-master-detail',
    templateUrl: './km-master-detail.component.html'
})
export class KmMasterDetailComponent implements OnInit, OnDestroy {

    kmMaster: KmMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kmMasterService: KmMasterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKmMasters();
    }

    load(id) {
        this.kmMasterService.find(id).subscribe((kmMaster) => {
            this.kmMaster = kmMaster;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKmMasters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kmMasterListModification',
            (response) => this.load(this.kmMaster.id)
        );
    }
}
