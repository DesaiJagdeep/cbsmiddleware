import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IActivityType, NewActivityType } from '../activity-type.model';

export type PartialUpdateActivityType = Partial<IActivityType> & Pick<IActivityType, 'id'>;

export type EntityResponseType = HttpResponse<IActivityType>;
export type EntityArrayResponseType = HttpResponse<IActivityType[]>;

@Injectable({ providedIn: 'root' })
export class ActivityTypeService {
  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/activity-types');

  constructor(protected http: HttpClient, protected applicationConfigService: ApplicationConfigService) {}

  create(activityType: NewActivityType): Observable<EntityResponseType> {
    return this.http.post<IActivityType>(this.resourceUrl, activityType, { observe: 'response' });
  }

  update(activityType: IActivityType): Observable<EntityResponseType> {
    return this.http.put<IActivityType>(`${this.resourceUrl}/${this.getActivityTypeIdentifier(activityType)}`, activityType, {
      observe: 'response',
    });
  }

  partialUpdate(activityType: PartialUpdateActivityType): Observable<EntityResponseType> {
    return this.http.patch<IActivityType>(`${this.resourceUrl}/${this.getActivityTypeIdentifier(activityType)}`, activityType, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActivityType>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActivityType[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getActivityTypeIdentifier(activityType: Pick<IActivityType, 'id'>): number {
    return activityType.id;
  }

  compareActivityType(o1: Pick<IActivityType, 'id'> | null, o2: Pick<IActivityType, 'id'> | null): boolean {
    return o1 && o2 ? this.getActivityTypeIdentifier(o1) === this.getActivityTypeIdentifier(o2) : o1 === o2;
  }

  addActivityTypeToCollectionIfMissing<Type extends Pick<IActivityType, 'id'>>(
    activityTypeCollection: Type[],
    ...activityTypesToCheck: (Type | null | undefined)[]
  ): Type[] {
    const activityTypes: Type[] = activityTypesToCheck.filter(isPresent);
    if (activityTypes.length > 0) {
      const activityTypeCollectionIdentifiers = activityTypeCollection.map(
        activityTypeItem => this.getActivityTypeIdentifier(activityTypeItem)!
      );
      const activityTypesToAdd = activityTypes.filter(activityTypeItem => {
        const activityTypeIdentifier = this.getActivityTypeIdentifier(activityTypeItem);
        if (activityTypeCollectionIdentifiers.includes(activityTypeIdentifier)) {
          return false;
        }
        activityTypeCollectionIdentifiers.push(activityTypeIdentifier);
        return true;
      });
      return [...activityTypesToAdd, ...activityTypeCollection];
    }
    return activityTypeCollection;
  }
}
