import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { KmLoans } from './km-loans.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class KmLoansService {

    private resourceUrl = 'api/km-loans';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(kmLoans: KmLoans): Observable<KmLoans> {
        const copy = this.convert(kmLoans);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(kmLoans: KmLoans): Observable<KmLoans> {
        const copy = this.convert(kmLoans);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<KmLoans> {
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
        entity.loanDate = this.dateUtils
            .convertDateTimeFromServer(entity.loanDate);
        entity.dueDate = this.dateUtils
            .convertDateTimeFromServer(entity.dueDate);
    }

    private convert(kmLoans: KmLoans): KmLoans {
        const copy: KmLoans = Object.assign({}, kmLoans);

        copy.loanDate = this.dateUtils.toDate(kmLoans.loanDate);

        copy.dueDate = this.dateUtils.toDate(kmLoans.dueDate);
        return copy;
    }
}
