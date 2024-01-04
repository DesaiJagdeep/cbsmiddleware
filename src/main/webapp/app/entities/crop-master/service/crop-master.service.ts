import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { map } from 'rxjs/operators';

import dayjs from 'dayjs/esm';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ICropMaster, NewCropMaster } from '../crop-master.model';

export type PartialUpdateCropMaster = Partial<ICropMaster> & Pick<ICropMaster, 'id'>;

type RestOf<T extends ICropMaster | NewCropMaster> = Omit<
  T,
  'vatapFromDay' | 'vatapToMonth' | 'lastToDay' | 'lastToMonth' | 'dueDay' | 'dueMonth'
> & {
  vatapFromDay?: string | null;
  vatapToMonth?: string | null;
  lastToDay?: string | null;
  lastToMonth?: string | null;
  dueDay?: string | null;
  dueMonth?: string | null;
};

export type RestCropMaster = RestOf<ICropMaster>;

export type NewRestCropMaster = RestOf<NewCropMaster>;

export type PartialUpdateRestCropMaster = RestOf<PartialUpdateCropMaster>;

export type EntityResponseType = HttpResponse<ICropMaster>;
export type EntityArrayResponseType = HttpResponse<ICropMaster[]>;

@Injectable({ providedIn: 'root' })
export class CropMasterService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/crop-masters');

  constructor(
    protected http: HttpClient,
    protected applicationConfigService: ApplicationConfigService,
  ) {}

  create(cropMaster: NewCropMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cropMaster);
    return this.http
      .post<RestCropMaster>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  update(cropMaster: ICropMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cropMaster);
    return this.http
      .put<RestCropMaster>(`${this.resourceUrl}/${this.getCropMasterIdentifier(cropMaster)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  partialUpdate(cropMaster: PartialUpdateCropMaster): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(cropMaster);
    return this.http
      .patch<RestCropMaster>(`${this.resourceUrl}/${this.getCropMasterIdentifier(cropMaster)}`, copy, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<RestCropMaster>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map(res => this.convertResponseFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<RestCropMaster[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map(res => this.convertResponseArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getCropMasterIdentifier(cropMaster: Pick<ICropMaster, 'id'>): number {
    return cropMaster.id;
  }

  compareCropMaster(o1: Pick<ICropMaster, 'id'> | null, o2: Pick<ICropMaster, 'id'> | null): boolean {
    return o1 && o2 ? this.getCropMasterIdentifier(o1) === this.getCropMasterIdentifier(o2) : o1 === o2;
  }

  addCropMasterToCollectionIfMissing<Type extends Pick<ICropMaster, 'id'>>(
    cropMasterCollection: Type[],
    ...cropMastersToCheck: (Type | null | undefined)[]
  ): Type[] {
    const cropMasters: Type[] = cropMastersToCheck.filter(isPresent);
    if (cropMasters.length > 0) {
      const cropMasterCollectionIdentifiers = cropMasterCollection.map(cropMasterItem => this.getCropMasterIdentifier(cropMasterItem)!);
      const cropMastersToAdd = cropMasters.filter(cropMasterItem => {
        const cropMasterIdentifier = this.getCropMasterIdentifier(cropMasterItem);
        if (cropMasterCollectionIdentifiers.includes(cropMasterIdentifier)) {
          return false;
        }
        cropMasterCollectionIdentifiers.push(cropMasterIdentifier);
        return true;
      });
      return [...cropMastersToAdd, ...cropMasterCollection];
    }
    return cropMasterCollection;
  }

  protected convertDateFromClient<T extends ICropMaster | NewCropMaster | PartialUpdateCropMaster>(cropMaster: T): RestOf<T> {
    return {
      ...cropMaster,
      vatapFromDay: cropMaster.vatapFromDay?.toJSON() ?? null,
      vatapToMonth: cropMaster.vatapToMonth?.toJSON() ?? null,
      lastToDay: cropMaster.lastToDay?.toJSON() ?? null,
      lastToMonth: cropMaster.lastToMonth?.toJSON() ?? null,
      dueDay: cropMaster.dueDay?.toJSON() ?? null,
      dueMonth: cropMaster.dueMonth?.toJSON() ?? null,
    };
  }

  protected convertDateFromServer(restCropMaster: RestCropMaster): ICropMaster {
    return {
      ...restCropMaster,
      vatapFromDay: restCropMaster.vatapFromDay ? dayjs(restCropMaster.vatapFromDay) : undefined,
      vatapToMonth: restCropMaster.vatapToMonth ? dayjs(restCropMaster.vatapToMonth) : undefined,
      lastToDay: restCropMaster.lastToDay ? dayjs(restCropMaster.lastToDay) : undefined,
      lastToMonth: restCropMaster.lastToMonth ? dayjs(restCropMaster.lastToMonth) : undefined,
      dueDay: restCropMaster.dueDay ? dayjs(restCropMaster.dueDay) : undefined,
      dueMonth: restCropMaster.dueMonth ? dayjs(restCropMaster.dueMonth) : undefined,
    };
  }

  protected convertResponseFromServer(res: HttpResponse<RestCropMaster>): HttpResponse<ICropMaster> {
    return res.clone({
      body: res.body ? this.convertDateFromServer(res.body) : null,
    });
  }

  protected convertResponseArrayFromServer(res: HttpResponse<RestCropMaster[]>): HttpResponse<ICropMaster[]> {
    return res.clone({
      body: res.body ? res.body.map(item => this.convertDateFromServer(item)) : null,
    });
  }
}
