import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { KmCrops } from './km-crops.model';
import { KmCropsService } from './km-crops.service';

@Component({
    selector: 'jhi-km-crops-detail',
    templateUrl: './km-crops-detail.component.html'
})
export class KmCropsDetailComponent implements OnInit, OnDestroy {

    kmCrops: KmCrops;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private kmCropsService: KmCropsService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInKmCrops();
    }

    load(id) {
        this.kmCropsService.find(id).subscribe((kmCrops) => {
            this.kmCrops = kmCrops;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInKmCrops() {
        this.eventSubscriber = this.eventManager.subscribe(
            'kmCropsListModification',
            (response) => this.load(this.kmCrops.id)
        );
    }
}
