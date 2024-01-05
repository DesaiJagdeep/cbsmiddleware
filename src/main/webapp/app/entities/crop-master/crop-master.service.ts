import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { CropMaster } from './crop-master.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CropMasterService {

    private resourceUrl = 'api/crop-masters';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(cropMaster: CropMaster): Observable<CropMaster> {
        const copy = this.convert(cropMaster);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(cropMaster: CropMaster): Observable<CropMaster> {
        const copy = this.convert(cropMaster);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<CropMaster> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.vatapFromDay = this.dateUtils
            .convertDateTimeFromServer(entity.vatapFromDay);
        entity.vatapToMonth = this.dateUtils
            .convertDateTimeFromServer(entity.vatapToMonth);
        entity.lastToDay = this.dateUtils
            .convertDateTimeFromServer(entity.lastToDay);
        entity.lastToMonth = this.dateUtils
            .convertDateTimeFromServer(entity.lastToMonth);
        entity.dueDay = this.dateUtils
            .convertDateTimeFromServer(entity.dueDay);
        entity.dueMonth = this.dateUtils
            .convertDateTimeFromServer(entity.dueMonth);
    }

    private convert(cropMaster: CropMaster): CropMaster {
        const copy: CropMaster = Object.assign({}, cropMaster);

        copy.vatapFromDay = this.dateUtils.toDate(cropMaster.vatapFromDay);

        copy.vatapToMonth = this.dateUtils.toDate(cropMaster.vatapToMonth);

        copy.lastToDay = this.dateUtils.toDate(cropMaster.lastToDay);

        copy.lastToMonth = this.dateUtils.toDate(cropMaster.lastToMonth);

        copy.dueDay = this.dateUtils.toDate(cropMaster.dueDay);

        copy.dueMonth = this.dateUtils.toDate(cropMaster.dueMonth);
        return copy;
    }
}
