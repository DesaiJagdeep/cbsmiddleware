import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CropMaster } from './crop-master.model';
import { CropMasterService } from './crop-master.service';

@Component({
    selector: 'jhi-crop-master-detail',
    templateUrl: './crop-master-detail.component.html'
})
export class CropMasterDetailComponent implements OnInit, OnDestroy {

    cropMaster: CropMaster;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private cropMasterService: CropMasterService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCropMasters();
    }

    load(id) {
        this.cropMasterService.find(id).subscribe((cropMaster) => {
            this.cropMaster = cropMaster;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCropMasters() {
        this.eventSubscriber = this.eventManager.subscribe(
            'cropMasterListModification',
            (response) => this.load(this.cropMaster.id)
        );
    }
}
